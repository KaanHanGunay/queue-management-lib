package tr.com.kaanhangunay.examples.messages;

import java.util.UUID;

public class QueueExceptionResolvedMessage implements BaseQueueMessage {
  private UUID messageId;

  public QueueExceptionResolvedMessage() {}

  public QueueExceptionResolvedMessage(UUID messageId) {
    this.messageId = messageId;
  }

  public UUID getMessageId() {
    return messageId;
  }

  public void setMessageId(UUID messageId) {
    this.messageId = messageId;
  }
}
