package tr.com.kaanhangunay.examples.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import tr.com.kaanhangunay.examples.messages.BaseQueueMessage;
import tr.com.kaanhangunay.examples.models.Destination;
import tr.com.kaanhangunay.examples.models.QueueMessageType;
import tr.com.kaanhangunay.examples.models.QueueMessageWrapper;

public class MessageSenderService {
  private final String appName;
  private final RabbitTemplate rabbitTemplate;

  public MessageSenderService(String appName, RabbitTemplate rabbitTemplate) {
    this.appName = appName;
    this.rabbitTemplate = rabbitTemplate;
  }

  public <T extends BaseQueueMessage> void to(
      QueueMessageType messageType, T body, Destination... destinations) {
    QueueMessageWrapper<T> message = new QueueMessageWrapper<>(messageType, body);
    message.setSource(appName);
    for (Destination destination : destinations) {
      rabbitTemplate.convertAndSend(destination.getServiceName(), message);
    }
  }
}
