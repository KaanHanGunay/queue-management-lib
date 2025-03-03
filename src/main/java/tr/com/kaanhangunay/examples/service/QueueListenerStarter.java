package tr.com.kaanhangunay.examples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class QueueListenerStarter implements ApplicationListener<ApplicationReadyEvent> {
  private static final Logger log = LoggerFactory.getLogger(QueueListenerStarter.class);

  private final SimpleMessageListenerContainer container;

  public QueueListenerStarter(SimpleMessageListenerContainer container) {
    this.container = container;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info("Queue listening is starting.");
    boolean haveError = false;
    try {
      container.start();
    } catch (Exception e) {
      log.error(
          "Queue listening failed to start. {}\nException: {}",
          e.getClass().getName(),
          e.getMessage());
      haveError = true;
    }
    if (!haveError) {
      log.info("Queue listening is started.");
    }
  }
}
