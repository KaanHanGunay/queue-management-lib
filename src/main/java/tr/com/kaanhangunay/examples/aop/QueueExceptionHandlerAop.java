package tr.com.kaanhangunay.examples.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import tr.com.kaanhangunay.examples.messages.QueueExceptionMessage;
import tr.com.kaanhangunay.examples.models.Destination;
import tr.com.kaanhangunay.examples.models.GenericApplicationEvent;
import tr.com.kaanhangunay.examples.models.QueueMessageType;
import tr.com.kaanhangunay.examples.service.MessageSenderService;

@Aspect
public class QueueExceptionHandlerAop {
  private final String appName;
  private final MessageSenderService messageSenderService;

  public QueueExceptionHandlerAop(MessageSenderService messageSenderService, String appName) {
    this.appName = appName;
    this.messageSenderService = messageSenderService;
  }

  @AfterThrowing(
      pointcut =
          "execution(* *..*.*(..)) && @annotation(org.springframework.context.event.EventListener)",
      throwing = "ex")
  public void handleEventListenerException(JoinPoint joinPoint, Throwable ex) {
    GenericApplicationEvent<?> applicationEvent =
        (GenericApplicationEvent<?>) joinPoint.getArgs()[0];
    QueueExceptionMessage exceptionMessage = new QueueExceptionMessage();
    exceptionMessage.setMessageId(applicationEvent.messageId());
    exceptionMessage.setType(applicationEvent.type());
    exceptionMessage.setDestination(appName);
    exceptionMessage.setSource(applicationEvent.source());
    exceptionMessage.setException(ex.getMessage());
    exceptionMessage.setBody(applicationEvent.body());
    messageSenderService.to(
        QueueMessageType.QUEUE_EXCEPTION_MESSAGE, exceptionMessage, Destination.EXCEPTION_HANDLER);
  }
}
