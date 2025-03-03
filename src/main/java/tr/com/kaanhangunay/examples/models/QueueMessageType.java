package tr.com.kaanhangunay.examples.models;

import tr.com.kaanhangunay.examples.messages.BaseQueueMessage;
import tr.com.kaanhangunay.examples.messages.ExampleMessage;

public enum QueueMessageType {
  EXAMPLE_MESSAGE(ExampleMessage.class);

  private final Class<? extends BaseQueueMessage> messageClass;

  QueueMessageType(Class<? extends BaseQueueMessage> messageClass) {
    this.messageClass = messageClass;
  }

  public Class<? extends BaseQueueMessage> getMessageClass() {
    return messageClass;
  }
}
