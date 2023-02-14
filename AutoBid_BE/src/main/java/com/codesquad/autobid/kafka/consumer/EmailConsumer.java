package com.codesquad.autobid.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumer {

    @KafkaListener(topics = {"EMAIL_TOPIC"})
    public void consumeMessage(String message) {
        log.debug("consumeMessage: {} ", message);
    }
}
