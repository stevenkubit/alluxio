/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.master.file.replication;

import static org.mockito.Mockito.mock;

import alluxio.AlluxioURI;
import alluxio.Constants;
import alluxio.conf.PropertyKey;
import alluxio.conf.ServerConfiguration;
import alluxio.grpc.CreateFilePOptions;
import alluxio.grpc.RegisterWorkerPOptions;
import alluxio.job.replicate.ReplicationHandler;
import alluxio.master.CoreMasterContext;
import alluxio.master.MasterRegistry;
import alluxio.master.MasterTestUtils;
import alluxio.master.block.BlockMaster;
import alluxio.master.block.BlockMasterFactory;
import alluxio.master.file.RpcContext;
import alluxio.master.file.contexts.CreateFileContext;
import alluxio.master.file.contexts.CreatePathContext;
import alluxio.master.file.meta.Inode;
import alluxio.master.file.meta.InodeDirectoryIdGenerator;
import alluxio.master.file.meta.InodeLockManager;
import alluxio.master.file.meta.InodeTree;
import alluxio.master.file.meta.InodeTree.LockPattern;
import alluxio.master.file.meta.LockedInodePath;
import alluxio.master.file.meta.MountTable;
import alluxio.master.file.meta.MutableInodeFile;
import alluxio.master.file.meta.options.MountInfo;
import alluxio.master.journal.JournalSystem;
import alluxio.master.journal.JournalTestUtils;
import alluxio.master.journal.NoopJournalContext;
import alluxio.master.metastore.InodeStore;
import alluxio.master.metastore.InodeStore.InodeStoreArgs;
import alluxio.master.metrics.MetricsMasterFactory;
import alluxio.metrics.Metric;
import alluxio.security.authorization.Mode;
import alluxio.underfs.UfsManager;
import alluxio.wire.WorkerNetAddress;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Unit tests for {@link ReplicationChecker}.
 */
public final class ReplicationCheckerTest {
  private static final String TEST_OWNER = "user1";
  private static final String TEST_GROUP = "";
  private static final Mode TEST_MODE = new Mode((short) 0755);
  private static final AlluxioURI TEST_FILE_1 = new AlluxioURI("/test1");
  private static final AlluxioURI TEST_FILE_2 = new AlluxioURI("/test2");
  private static final List<Long> NO_BLOCKS = ImmutableList.of();
  private static final List<Metric> NO_METRICS = ImmutableList.of();
  private static final Map<String, List<Long>> NO_BLOCKS_ON_TIERS = ImmutableMap.of();
  private static final Map<Long, Integer> EMPTY = ImmutableMap.of();

  /**
   * A mock class of AdjustReplicationHandler, used to test the output of ReplicationChecker.
   */
  @ThreadSafe
  private static class MockHandler implements ReplicationHandler {
    private final Map<Long, Integer> mEvictRequests = Maps.newHashMap();
    private final Map<Long, Integer> mReplicateRequests = Maps.newHashMap();

    @Override
    public long evict(AlluxioURI uri, long blockId, int numReplicas) {
      mEvictRequests.put(blockId, numReplicas);
      return 0;
    }

    @Override
    public long replicate(AlluxioURI uri, long blockId, int numReplicas) {
      mReplicateRequests.put(blockId, numReplicas);
      return 0;
    }

    public Map<Long, Integer> getEvictRequests() {
      return mEvictRequests;
    }

    public Map<Long, Integer> getReplicateRequests() {
      return mReplicateRequests;
    }
  }

  private InodeStore mInodeStore;
  private InodeTree mInodeTree;
  private BlockMaster mBlockMaster;
  private ReplicationChecker mReplicationChecker;
  private MockHandler mMockReplicationHandler;
  private CreateFileContext mFileContext =
      CreateFileContext.defaults(CreateFilePOptions.newBuilder().setBlockSizeBytes(Constants.KB)
          .setMode(TEST_MODE.toProto())).setOwner(TEST_OWNER).setGroup(TEST_GROUP);
  private Set<Long> mKnownWorkers = Sets.newHashSet();

  /** Rule to create a new temporary folder during each test. */
  @Rule
  public TemporaryFolder mTestFolder = new TemporaryFolder();

  @Before
  public void before() throws Exception {
    MasterRegistry registry = new MasterRegistry();
    JournalSystem journalSystem = JournalTestUtils.createJournalSystem(mTestFolder);
    CoreMasterContext context = MasterTestUtils.testMasterContext(journalSystem);
    new MetricsMasterFactory().create(registry, context);
    mBlockMaster = new BlockMasterFactory().create(registry, context);
    InodeDirectoryIdGenerator directoryIdGenerator = new InodeDirectoryIdGenerator(mBlockMaster);
    UfsManager manager = mock(UfsManager.class);
    MountTable mountTable = new MountTable(manager, mock(MountInfo.class));
    InodeLockManager lockManager = new InodeLockManager();
    mInodeStore = context.getInodeStoreFactory()
        .apply(new InodeStoreArgs(lockManager, ServerConfiguration.global()));
    mInodeTree =
        new InodeTree(mInodeStore, mBlockMaster, directoryIdGenerator, mountTable, lockManager);

    journalSystem.start();
    journalSystem.gainPrimacy();
    mBlockMaster.start(true);

    ServerConfiguration.set(PropertyKey.SECURITY_AUTHORIZATION_PERMISSION_ENABLED, "true");
    ServerConfiguration
        .set(PropertyKey.SECURITY_AUTHORIZATION_PERMISSION_SUPERGROUP, "test-supergroup");
    mInodeTree.initializeRoot(TEST_OWNER, TEST_GROUP, TEST_MODE, NoopJournalContext.INSTANCE);

    mMockReplicationHandler = new MockHandler();
    mReplicationChecker = new ReplicationChecker(mInodeTree, mBlockMaster,
        context.getSafeModeManager(), mMockReplicationHandler);
  }

  @After
  public void after() {
    ServerConfiguration.reset();
  }

  /**
   * Helper to create a file with a single block.
   *
   * @param path Alluxio path of the file
   * @param context context to create the file
   * @return the block ID
   */
  private long createBlockHelper(AlluxioURI path, CreatePathContext<?, ?> context)
      throws Exception {
    try (LockedInodePath inodePath = mInodeTree.lockInodePath(path, LockPattern.WRITE_EDGE)) {
      List<Inode> created = mInodeTree.createPath(RpcContext.NOOP, inodePath, context);

      MutableInodeFile inodeFile = mInodeStore.getMutable(created.get(0).getId()).get().asFile();
      inodeFile.setBlockSizeBytes(1);
      inodeFile.setBlockIds(Arrays.asList(inodeFile.getNewBlockId()));
      inodeFile.setCompleted(true);
      mInodeStore.writeInode(inodeFile);
      return inodeFile.getBlockIdByIndex(0);
    }
  }

  /**
   * Helper to create and add a given number of locations for block ID.
   *
   * @param blockId ID of the block to add location
   * @param numLocations number of locations to add
   */
  private void addBlockLocationHelper(long blockId, int numLocations) throws Exception {
    // Commit blockId to the first worker.
    mBlockMaster.commitBlock(createWorkerHelper(0), 50L, "MEM", blockId, 20L);

    // Send a heartbeat from other workers saying that it's added blockId.
    for (int i = 1; i < numLocations; i++) {
      heartbeatToAddLocationHelper(blockId, createWorkerHelper(i));
    }
  }

  /**
   * Helper to register a new worker.
   *
   * @param workerIndex the index of the worker in all workers
   * @return the created worker ID
   */
  private long createWorkerHelper(int workerIndex) throws Exception {
    WorkerNetAddress address = new WorkerNetAddress().setHost("host" + workerIndex).setRpcPort(1000)
        .setDataPort(2000).setWebPort(3000);
    long workerId = mBlockMaster.getWorkerId(address);
    if (!mKnownWorkers.contains(workerId)) {
      // Do not re-register works, otherwise added block will be removed
      mBlockMaster.workerRegister(workerId, ImmutableList.of("MEM"), ImmutableMap.of("MEM", 100L),
          ImmutableMap.of("MEM", 0L), NO_BLOCKS_ON_TIERS,
          RegisterWorkerPOptions.getDefaultInstance());
      mKnownWorkers.add(workerId);
    }
    return workerId;
  }

  /**
   * Helper to heartbeat to a worker and report a newly added block.
   *
   * @param blockId ID of the block to add location
   * @param workerId ID of the worker to heartbeat
   */
  private void heartbeatToAddLocationHelper(long blockId, long workerId) throws Exception {
    List<Long> addedBlocks = ImmutableList.of(blockId);
    mBlockMaster.workerHeartbeat(workerId, null, ImmutableMap.of("MEM", 0L), NO_BLOCKS,
        ImmutableMap.of("MEM", addedBlocks), NO_METRICS);
  }

  @Test
  public void heartbeatWhenTreeIsEmpty() throws Exception {
    mReplicationChecker.heartbeat();
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFileWithinRange() throws Exception {
    mFileContext.getOptions().setReplicationMin(1).setReplicationMax(3);
    long blockId =
        createBlockHelper(TEST_FILE_1, mFileContext);
    // One replica, meeting replication min
    addBlockLocationHelper(blockId, 1);
    mReplicationChecker.heartbeat();
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());

    // Two replicas, good
    heartbeatToAddLocationHelper(blockId, createWorkerHelper(1));
    mReplicationChecker.heartbeat();
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());

    // Three replicas, meeting replication max, still good
    heartbeatToAddLocationHelper(blockId, createWorkerHelper(2));
    mReplicationChecker.heartbeat();
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFileUnderReplicatedBy1() throws Exception {
    mFileContext.getOptions().setReplicationMin(1);
    long blockId = createBlockHelper(TEST_FILE_1, mFileContext);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected = ImmutableMap.of(blockId, 1);
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(expected, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFileUnderReplicatedBy10() throws Exception {
    mFileContext.getOptions().setReplicationMin(10);
    long blockId = createBlockHelper(TEST_FILE_1, mFileContext);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected = ImmutableMap.of(blockId, 10);
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(expected, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatMultipleFilesUnderReplicated() throws Exception {
    mFileContext.getOptions().setReplicationMin(1);
    long blockId1 = createBlockHelper(TEST_FILE_1, mFileContext);
    mFileContext.getOptions().setReplicationMin(2);
    long blockId2 = createBlockHelper(TEST_FILE_2, mFileContext);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected = ImmutableMap.of(blockId1, 1, blockId2, 2);
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(expected, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFileUnderReplicatedAndLost() throws Exception {
    mFileContext.getOptions().setReplicationMin(2);
    long blockId = createBlockHelper(TEST_FILE_1, mFileContext);

    // Create a worker.
    long workerId = mBlockMaster.getWorkerId(new WorkerNetAddress().setHost("localhost")
        .setRpcPort(80).setDataPort(81).setWebPort(82));
    mBlockMaster.workerRegister(workerId, Collections.singletonList("MEM"),
        ImmutableMap.of("MEM", 100L), ImmutableMap.of("MEM", 0L), NO_BLOCKS_ON_TIERS,
        RegisterWorkerPOptions.getDefaultInstance());
    mBlockMaster.commitBlock(workerId, 50L, "MEM", blockId, 20L);

    // Indicate that blockId is removed on the worker.
    mBlockMaster.workerHeartbeat(workerId, null, ImmutableMap.of("MEM", 0L),
        ImmutableList.of(blockId), NO_BLOCKS_ON_TIERS, NO_METRICS);

    mReplicationChecker.heartbeat();
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFileOverReplicatedBy1() throws Exception {
    mFileContext.getOptions().setReplicationMax(1);
    long blockId = createBlockHelper(TEST_FILE_1, mFileContext);
    addBlockLocationHelper(blockId, 2);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected = ImmutableMap.of(blockId, 1);
    Assert.assertEquals(expected, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFileOverReplicatedBy10() throws Exception {
    mFileContext.getOptions().setReplicationMax(1);
    long blockId = createBlockHelper(TEST_FILE_1, mFileContext);
    addBlockLocationHelper(blockId, 11);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected = ImmutableMap.of(blockId, 10);
    Assert.assertEquals(expected, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatMultipleFilesOverReplicated() throws Exception {
    mFileContext.getOptions().setReplicationMax(1);
    long blockId1 = createBlockHelper(TEST_FILE_1, mFileContext);
    mFileContext.getOptions().setReplicationMax(2);
    long blockId2 = createBlockHelper(TEST_FILE_2, mFileContext);
    addBlockLocationHelper(blockId1, 2);
    addBlockLocationHelper(blockId2, 4);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected = ImmutableMap.of(blockId1, 1, blockId2, 2);
    Assert.assertEquals(expected, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(EMPTY, mMockReplicationHandler.getReplicateRequests());
  }

  @Test
  public void heartbeatFilesUnderAndOverReplicated() throws Exception {
    mFileContext.getOptions().setReplicationMin(2).setReplicationMax(-1);
    long blockId1 = createBlockHelper(TEST_FILE_1, mFileContext);
    mFileContext.getOptions().setReplicationMin(0).setReplicationMax(3);
    long blockId2 = createBlockHelper(TEST_FILE_2, mFileContext);
    addBlockLocationHelper(blockId1, 1);
    addBlockLocationHelper(blockId2, 5);

    mReplicationChecker.heartbeat();
    Map<Long, Integer> expected1 = ImmutableMap.of(blockId1, 1);
    Map<Long, Integer> expected2 = ImmutableMap.of(blockId2, 2);

    Assert.assertEquals(expected2, mMockReplicationHandler.getEvictRequests());
    Assert.assertEquals(expected1, mMockReplicationHandler.getReplicateRequests());
  }
}
