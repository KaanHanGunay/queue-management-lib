package tr.com.kaanhangunay.examples.messages;

public class ExampleMessage implements BaseQueueMessage {
  private String message;

  public ExampleMessage() {}

  public ExampleMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
