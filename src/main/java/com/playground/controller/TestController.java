package com.playground.controller;

import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TestController {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @GetMapping("/push-message")
  public String pushMessage() throws ExecutionException, InterruptedException {
    kafkaTemplate.send("demo-topic", "Hello World!").get();
    return "Success";
  }
}
