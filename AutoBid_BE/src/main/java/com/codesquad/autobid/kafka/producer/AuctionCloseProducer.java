package com.codesquad.autobid.kafka.producer;

import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionCloseProducer {

    @Value("${spring.kafka.topic.auction-close}")
    private String AUCTION_CLOSE_TOPIC_NAME;
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper om;

    public void produce(List<AuctionKafkaDTO> auctions) throws JsonProcessingException {
        for (AuctionKafkaDTO auction : auctions) {
            kafkaTemplate.send(AUCTION_CLOSE_TOPIC_NAME, new String(om.writeValueAsBytes(auction)));
        }
    }
}
