package com.codesquad.autobid.kafka.producer;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionOpenProducer {

    @Value("${spring.kafka.topic.auction-open}")
    private String AUCTION_OPEN_TOPIC_NAME;
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper om;

    public void produce(List<AuctionKafkaDTO> auctions) throws JsonProcessingException {
        for (AuctionKafkaDTO auction : auctions) {
            log.info("auction : {}", auction.getAuctionId());
            kafkaTemplate.send(AUCTION_OPEN_TOPIC_NAME, new String(om.writeValueAsBytes(auction)));
        }
    }
}
