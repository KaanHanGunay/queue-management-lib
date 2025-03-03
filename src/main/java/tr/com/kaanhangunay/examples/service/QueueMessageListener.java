package tr.com.kaanhangunay.examples.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationEventPublisher;
import tr.com.kaanhangunay.examples.messages.BaseQueueMessage;
import tr.com.kaanhangunay.examples.models.GenericApplicationEvent;
import tr.com.kaanhangunay.examples.models.QueueMessageType;
import tr.com.kaanhangunay.examples.models.QueueMessageWrapper;

public class QueueMessageListener implements MessageListener {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final ApplicationEventPublisher applicationEventPublisher;

  public QueueMessageListener(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void onMessage(Message message) {
    try {
      String body = new String(message.getBody());
      JsonNode rootNode = objectMapper.readTree(body);
      QueueMessageType type =
          objectMapper.treeToValue(rootNode.get("type"), QueueMessageType.class);
      Class<? extends BaseQueueMessage> messageClass = type.getMessageClass();
      JavaType wrapperType =
          objectMapper
              .getTypeFactory()
              .constructParametricType(QueueMessageWrapper.class, messageClass);
      QueueMessageWrapper<?> queueMessage = objectMapper.readValue(body, wrapperType);
      applicationEventPublisher.publishEvent(
          new GenericApplicationEvent<>(
              queueMessage.getMessageId(),
              queueMessage.getType(),
              queueMessage.getBody(),
              queueMessage.getSource()));
    } catch (JsonProcessingException | IllegalArgumentException | ClassCastException e) {
      /* Message could be invalid json or invalid message type. This caches these types of exceptions. */
    } catch (Exception e) {
      /* These exceptions handled by the queue exception handler */
    }
  }
}
