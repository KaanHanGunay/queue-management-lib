package tr.com.kaanhangunay.examples.models;

import java.util.UUID;
import tr.com.kaanhangunay.examples.messages.BaseQueueMessage;

public class QueueMessageWrapper<T extends BaseQueueMessage> {
  private UUID messageId = UUID.randomUUID();
  private QueueMessageType type;
  private T body;
  private String source;
  private Boolean isRetry = false;

  public QueueMessageWrapper() {}

  public QueueMessageWrapper(QueueMessageType type, T body) {
    this.type = type;
    this.body = body;
  }

  public UUID getMessageId() {
    return messageId;
  }

  public void setMessageId(UUID messageId) {
    this.messageId = messageId;
  }

  public QueueMessageType getType() {
    return type;
  }

  public void setType(QueueMessageType type) {
    this.type = type;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Boolean isRetry() {
    return isRetry;
  }

  public void setRetry(Boolean retry) {
    isRetry = retry;
  }
}
