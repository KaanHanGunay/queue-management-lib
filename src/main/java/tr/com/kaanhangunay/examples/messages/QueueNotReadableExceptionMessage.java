package tr.com.kaanhangunay.examples.messages;

public class QueueNotReadableExceptionMessage implements BaseQueueMessage {
  private String destination;
  private String body;
  private String exception;

  public QueueNotReadableExceptionMessage() {}

  public QueueNotReadableExceptionMessage(String destination, String body, String exception) {
    this.destination = destination;
    this.body = body;
    this.exception = exception;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }
}
