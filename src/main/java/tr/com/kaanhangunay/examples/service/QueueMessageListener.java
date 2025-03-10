package tr.com.kaanhangunay.examples.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationEventPublisher;
import tr.com.kaanhangunay.examples.messages.BaseQueueMessage;
import tr.com.kaanhangunay.examples.messages.QueueExceptionResolvedMessage;
import tr.com.kaanhangunay.examples.messages.QueueNotReadableExceptionMessage;
import tr.com.kaanhangunay.examples.models.Destination;
import tr.com.kaanhangunay.examples.models.GenericApplicationEvent;
import tr.com.kaanhangunay.examples.models.QueueMessageType;
import tr.com.kaanhangunay.examples.models.QueueMessageWrapper;

public class QueueMessageListener implements MessageListener {
  private static final Logger log = LoggerFactory.getLogger(QueueMessageListener.class);

  private final String appName;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final MessageSenderService messageSenderService;
  private final ApplicationEventPublisher applicationEventPublisher;

  public QueueMessageListener(
      String appName,
      MessageSenderService messageSenderService,
      ApplicationEventPublisher applicationEventPublisher) {
    this.appName = appName;
    this.messageSenderService = messageSenderService;
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
      if (queueMessage.isRetry() != null && queueMessage.isRetry()) {
        QueueExceptionResolvedMessage resolvedMessage = new QueueExceptionResolvedMessage();
        resolvedMessage.setMessageId(queueMessage.getMessageId());
        messageSenderService.to(
            QueueMessageType.QUEUE_EXCEPTION_RESOLVED_MESSAGE,
            resolvedMessage,
            Destination.EXCEPTION_HANDLER);
      }
    } catch (JsonProcessingException | IllegalArgumentException | ClassCastException e) {
      log.error("Failed to parse queue message: {}", e.getMessage());
      QueueNotReadableExceptionMessage notReadableException =
          new QueueNotReadableExceptionMessage();
      notReadableException.setDestination(appName);
      notReadableException.setBody(new String(message.getBody()));
      notReadableException.setException(e.getMessage());
      messageSenderService.to(
          QueueMessageType.QUEUE_NOT_READABLE_MESSAGE,
          notReadableException,
          Destination.EXCEPTION_HANDLER);
    } catch (Exception e) {
      /* These exceptions handled by the queue exception handler */
    }
  }
}
