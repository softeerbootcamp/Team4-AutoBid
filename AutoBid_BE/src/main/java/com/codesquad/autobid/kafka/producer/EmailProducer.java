package com.codesquad.autobid.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailProducer {

    @Value("${spring.kafka.email.topic}")
    private String EMAIL_TOPIC;

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public EmailProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage() {
        log.debug("produceMessage: {}", "message");
        kafkaTemplate.send(EMAIL_TOPIC, "message");
    }
}
