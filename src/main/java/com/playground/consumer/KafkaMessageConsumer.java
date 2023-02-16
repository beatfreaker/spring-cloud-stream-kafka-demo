package com.playground.consumer;


import static com.playground.consumer.KafkaMessageConsumer.BEAN_NAME;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component(BEAN_NAME)
public class KafkaMessageConsumer implements Consumer<Message<String>> {

  public final static String BEAN_NAME = "kafkaMessageConsumer";

  @Override
  public void accept(Message<String> stringMessage) {
    log.info("message :: {}", stringMessage);
  }
}
