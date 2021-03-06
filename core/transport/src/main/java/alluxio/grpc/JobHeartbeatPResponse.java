// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpc/job_master.proto

package alluxio.grpc;

/**
 * Protobuf type {@code alluxio.grpc.job.JobHeartbeatPResponse}
 */
public  final class JobHeartbeatPResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:alluxio.grpc.job.JobHeartbeatPResponse)
    JobHeartbeatPResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use JobHeartbeatPResponse.newBuilder() to construct.
  private JobHeartbeatPResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private JobHeartbeatPResponse() {
    commands_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private JobHeartbeatPResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              commands_ = new java.util.ArrayList<alluxio.grpc.JobCommand>();
              mutable_bitField0_ |= 0x00000001;
            }
            commands_.add(
                input.readMessage(alluxio.grpc.JobCommand.PARSER, extensionRegistry));
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        commands_ = java.util.Collections.unmodifiableList(commands_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return alluxio.grpc.JobMasterProto.internal_static_alluxio_grpc_job_JobHeartbeatPResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return alluxio.grpc.JobMasterProto.internal_static_alluxio_grpc_job_JobHeartbeatPResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            alluxio.grpc.JobHeartbeatPResponse.class, alluxio.grpc.JobHeartbeatPResponse.Builder.class);
  }

  public static final int COMMANDS_FIELD_NUMBER = 1;
  private java.util.List<alluxio.grpc.JobCommand> commands_;
  /**
   * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
   */
  public java.util.List<alluxio.grpc.JobCommand> getCommandsList() {
    return commands_;
  }
  /**
   * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
   */
  public java.util.List<? extends alluxio.grpc.JobCommandOrBuilder> 
      getCommandsOrBuilderList() {
    return commands_;
  }
  /**
   * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
   */
  public int getCommandsCount() {
    return commands_.size();
  }
  /**
   * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
   */
  public alluxio.grpc.JobCommand getCommands(int index) {
    return commands_.get(index);
  }
  /**
   * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
   */
  public alluxio.grpc.JobCommandOrBuilder getCommandsOrBuilder(
      int index) {
    return commands_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < commands_.size(); i++) {
      output.writeMessage(1, commands_.get(i));
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < commands_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, commands_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof alluxio.grpc.JobHeartbeatPResponse)) {
      return super.equals(obj);
    }
    alluxio.grpc.JobHeartbeatPResponse other = (alluxio.grpc.JobHeartbeatPResponse) obj;

    boolean result = true;
    result = result && getCommandsList()
        .equals(other.getCommandsList());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getCommandsCount() > 0) {
      hash = (37 * hash) + COMMANDS_FIELD_NUMBER;
      hash = (53 * hash) + getCommandsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static alluxio.grpc.JobHeartbeatPResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(alluxio.grpc.JobHeartbeatPResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code alluxio.grpc.job.JobHeartbeatPResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:alluxio.grpc.job.JobHeartbeatPResponse)
      alluxio.grpc.JobHeartbeatPResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return alluxio.grpc.JobMasterProto.internal_static_alluxio_grpc_job_JobHeartbeatPResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return alluxio.grpc.JobMasterProto.internal_static_alluxio_grpc_job_JobHeartbeatPResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              alluxio.grpc.JobHeartbeatPResponse.class, alluxio.grpc.JobHeartbeatPResponse.Builder.class);
    }

    // Construct using alluxio.grpc.JobHeartbeatPResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getCommandsFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (commandsBuilder_ == null) {
        commands_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        commandsBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return alluxio.grpc.JobMasterProto.internal_static_alluxio_grpc_job_JobHeartbeatPResponse_descriptor;
    }

    public alluxio.grpc.JobHeartbeatPResponse getDefaultInstanceForType() {
      return alluxio.grpc.JobHeartbeatPResponse.getDefaultInstance();
    }

    public alluxio.grpc.JobHeartbeatPResponse build() {
      alluxio.grpc.JobHeartbeatPResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public alluxio.grpc.JobHeartbeatPResponse buildPartial() {
      alluxio.grpc.JobHeartbeatPResponse result = new alluxio.grpc.JobHeartbeatPResponse(this);
      int from_bitField0_ = bitField0_;
      if (commandsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          commands_ = java.util.Collections.unmodifiableList(commands_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.commands_ = commands_;
      } else {
        result.commands_ = commandsBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof alluxio.grpc.JobHeartbeatPResponse) {
        return mergeFrom((alluxio.grpc.JobHeartbeatPResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(alluxio.grpc.JobHeartbeatPResponse other) {
      if (other == alluxio.grpc.JobHeartbeatPResponse.getDefaultInstance()) return this;
      if (commandsBuilder_ == null) {
        if (!other.commands_.isEmpty()) {
          if (commands_.isEmpty()) {
            commands_ = other.commands_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureCommandsIsMutable();
            commands_.addAll(other.commands_);
          }
          onChanged();
        }
      } else {
        if (!other.commands_.isEmpty()) {
          if (commandsBuilder_.isEmpty()) {
            commandsBuilder_.dispose();
            commandsBuilder_ = null;
            commands_ = other.commands_;
            bitField0_ = (bitField0_ & ~0x00000001);
            commandsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getCommandsFieldBuilder() : null;
          } else {
            commandsBuilder_.addAllMessages(other.commands_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      alluxio.grpc.JobHeartbeatPResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (alluxio.grpc.JobHeartbeatPResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<alluxio.grpc.JobCommand> commands_ =
      java.util.Collections.emptyList();
    private void ensureCommandsIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        commands_ = new java.util.ArrayList<alluxio.grpc.JobCommand>(commands_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        alluxio.grpc.JobCommand, alluxio.grpc.JobCommand.Builder, alluxio.grpc.JobCommandOrBuilder> commandsBuilder_;

    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public java.util.List<alluxio.grpc.JobCommand> getCommandsList() {
      if (commandsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(commands_);
      } else {
        return commandsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public int getCommandsCount() {
      if (commandsBuilder_ == null) {
        return commands_.size();
      } else {
        return commandsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public alluxio.grpc.JobCommand getCommands(int index) {
      if (commandsBuilder_ == null) {
        return commands_.get(index);
      } else {
        return commandsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder setCommands(
        int index, alluxio.grpc.JobCommand value) {
      if (commandsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommandsIsMutable();
        commands_.set(index, value);
        onChanged();
      } else {
        commandsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder setCommands(
        int index, alluxio.grpc.JobCommand.Builder builderForValue) {
      if (commandsBuilder_ == null) {
        ensureCommandsIsMutable();
        commands_.set(index, builderForValue.build());
        onChanged();
      } else {
        commandsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder addCommands(alluxio.grpc.JobCommand value) {
      if (commandsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommandsIsMutable();
        commands_.add(value);
        onChanged();
      } else {
        commandsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder addCommands(
        int index, alluxio.grpc.JobCommand value) {
      if (commandsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommandsIsMutable();
        commands_.add(index, value);
        onChanged();
      } else {
        commandsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder addCommands(
        alluxio.grpc.JobCommand.Builder builderForValue) {
      if (commandsBuilder_ == null) {
        ensureCommandsIsMutable();
        commands_.add(builderForValue.build());
        onChanged();
      } else {
        commandsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder addCommands(
        int index, alluxio.grpc.JobCommand.Builder builderForValue) {
      if (commandsBuilder_ == null) {
        ensureCommandsIsMutable();
        commands_.add(index, builderForValue.build());
        onChanged();
      } else {
        commandsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder addAllCommands(
        java.lang.Iterable<? extends alluxio.grpc.JobCommand> values) {
      if (commandsBuilder_ == null) {
        ensureCommandsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, commands_);
        onChanged();
      } else {
        commandsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder clearCommands() {
      if (commandsBuilder_ == null) {
        commands_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        commandsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public Builder removeCommands(int index) {
      if (commandsBuilder_ == null) {
        ensureCommandsIsMutable();
        commands_.remove(index);
        onChanged();
      } else {
        commandsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public alluxio.grpc.JobCommand.Builder getCommandsBuilder(
        int index) {
      return getCommandsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public alluxio.grpc.JobCommandOrBuilder getCommandsOrBuilder(
        int index) {
      if (commandsBuilder_ == null) {
        return commands_.get(index);  } else {
        return commandsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public java.util.List<? extends alluxio.grpc.JobCommandOrBuilder> 
         getCommandsOrBuilderList() {
      if (commandsBuilder_ != null) {
        return commandsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(commands_);
      }
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public alluxio.grpc.JobCommand.Builder addCommandsBuilder() {
      return getCommandsFieldBuilder().addBuilder(
          alluxio.grpc.JobCommand.getDefaultInstance());
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public alluxio.grpc.JobCommand.Builder addCommandsBuilder(
        int index) {
      return getCommandsFieldBuilder().addBuilder(
          index, alluxio.grpc.JobCommand.getDefaultInstance());
    }
    /**
     * <code>repeated .alluxio.grpc.job.JobCommand commands = 1;</code>
     */
    public java.util.List<alluxio.grpc.JobCommand.Builder> 
         getCommandsBuilderList() {
      return getCommandsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        alluxio.grpc.JobCommand, alluxio.grpc.JobCommand.Builder, alluxio.grpc.JobCommandOrBuilder> 
        getCommandsFieldBuilder() {
      if (commandsBuilder_ == null) {
        commandsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            alluxio.grpc.JobCommand, alluxio.grpc.JobCommand.Builder, alluxio.grpc.JobCommandOrBuilder>(
                commands_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        commands_ = null;
      }
      return commandsBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:alluxio.grpc.job.JobHeartbeatPResponse)
  }

  // @@protoc_insertion_point(class_scope:alluxio.grpc.job.JobHeartbeatPResponse)
  private static final alluxio.grpc.JobHeartbeatPResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new alluxio.grpc.JobHeartbeatPResponse();
  }

  public static alluxio.grpc.JobHeartbeatPResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  @java.lang.Deprecated public static final com.google.protobuf.Parser<JobHeartbeatPResponse>
      PARSER = new com.google.protobuf.AbstractParser<JobHeartbeatPResponse>() {
    public JobHeartbeatPResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new JobHeartbeatPResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<JobHeartbeatPResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<JobHeartbeatPResponse> getParserForType() {
    return PARSER;
  }

  public alluxio.grpc.JobHeartbeatPResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

