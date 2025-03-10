package tr.com.kaanhangunay.examples.models;

public enum Destination {
  MESSAGE_SENDER("message-sender"),
  MESSAGE_RECEIVER("message-receiver"),
  EXCEPTION_HANDLER("exception-handler");

  private final String serviceName;

  Destination(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceName() {
    return serviceName;
  }
}
