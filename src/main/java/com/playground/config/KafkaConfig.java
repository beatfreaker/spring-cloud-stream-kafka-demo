package com.playground.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;

@Configuration
public class KafkaConfig {

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    Map<String, Object> configProps = getConfigProps();
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  private Map<String, Object> getConfigProps() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ProducerConfig.ACKS_CONFIG, "all");
    return configProps;
  }

  @Bean
  public ContainerCustomizer<String, Message, ConcurrentMessageListenerContainer<String, Message>> containerCustomizer(
      ConcurrentKafkaListenerContainerFactory<String, Message> factory) {
    ContainerCustomizer<String, Message, ConcurrentMessageListenerContainer<String, Message>> cust = container -> {
      container.getContainerProperties().setDeliveryAttemptHeader(true);
    };
    factory.setContainerCustomizer(cust);
    return cust;
  }
}
