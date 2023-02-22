package com.codesquad.autobid.bid.kafka;

import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidRollbackProducer {

    @Value("${spring.kafka.topic.redis-rollback}")
    private String AUCTION_OPEN_TOPIC_NAME;
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper om;

    public void produce(BidRegisterRequest bidRegisterRequest) {
        log.error("RedisRollbackProducer: {}", bidRegisterRequest);
        try {
            kafkaTemplate.send(AUCTION_OPEN_TOPIC_NAME, new String(om.writeValueAsBytes(bidRegisterRequest)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
