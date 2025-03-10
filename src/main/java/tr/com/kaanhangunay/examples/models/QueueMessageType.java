package tr.com.kaanhangunay.examples.models;

import tr.com.kaanhangunay.examples.messages.*;

public enum QueueMessageType {
  EXAMPLE_MESSAGE(ExampleMessage.class),
  QUEUE_EXCEPTION_MESSAGE(QueueExceptionMessage.class),
  QUEUE_EXCEPTION_RESOLVED_MESSAGE(QueueExceptionResolvedMessage.class),
  QUEUE_NOT_READABLE_MESSAGE(QueueNotReadableExceptionMessage.class);

  private final Class<? extends BaseQueueMessage> messageClass;

  QueueMessageType(Class<? extends BaseQueueMessage> messageClass) {
    this.messageClass = messageClass;
  }

  public Class<? extends BaseQueueMessage> getMessageClass() {
    return messageClass;
  }
}
