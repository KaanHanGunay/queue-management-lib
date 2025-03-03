package tr.com.kaanhangunay.examples.configs;

import java.nio.charset.StandardCharsets;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import tr.com.kaanhangunay.examples.service.MessageSenderService;
import tr.com.kaanhangunay.examples.service.QueueListenerStarter;
import tr.com.kaanhangunay.examples.service.QueueMessageListener;

@Configuration
public class AmqpConfiguration {
  @Bean
  @ConditionalOnProperty(value = "spring.application.name")
  public Queue queueRegisterer(Environment environment) {
    String appName = environment.getProperty("spring.application.name");
    return new Queue(appName, true);
  }

  @Bean
  @DependsOn("queueRegisterer")
  public SimpleMessageListenerContainer queueMessageListener(
      ConnectionFactory connectionFactory,
      Environment environment,
      ApplicationEventPublisher applicationEventPublisher) {
    String appName = environment.getProperty("spring.application.name");
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(appName);
    container.setMessageListener(new QueueMessageListener(applicationEventPublisher));
    return container;
  }

  @Bean
  @DependsOn("queueMessageListener")
  public QueueListenerStarter applicationStartupListener(SimpleMessageListenerContainer container) {
    return new QueueListenerStarter(container);
  }

  @Bean
  public MessageSenderService messageSenderService(
      RabbitTemplate rabbitTemplate, Environment environment) {
    String appName = environment.getProperty("spring.application.name");
    rabbitTemplate.setEncoding(StandardCharsets.UTF_8.name());
    return new MessageSenderService(appName, rabbitTemplate);
  }

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
