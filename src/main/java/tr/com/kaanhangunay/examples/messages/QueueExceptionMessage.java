package tr.com.kaanhangunay.examples.messages;

import java.util.UUID;
import tr.com.kaanhangunay.examples.models.QueueMessageType;

public class QueueExceptionMessage implements BaseQueueMessage {
  private UUID messageId;
  private String destination;
  private QueueMessageType type;
  private Object body;
  private String exception;
  private String source;

  public QueueExceptionMessage() {}

  public QueueExceptionMessage(
      UUID messageId,
      String destination,
      QueueMessageType type,
      Object body,
      String exception,
      String source) {
    this.messageId = messageId;
    this.destination = destination;
    this.type = type;
    this.body = body;
    this.exception = exception;
    this.source = source;
  }

  public UUID getMessageId() {
    return messageId;
  }

  public void setMessageId(UUID messageId) {
    this.messageId = messageId;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public QueueMessageType getType() {
    return type;
  }

  public void setType(QueueMessageType type) {
    this.type = type;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
