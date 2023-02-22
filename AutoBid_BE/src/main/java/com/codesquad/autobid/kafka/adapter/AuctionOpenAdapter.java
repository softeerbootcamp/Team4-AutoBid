package com.codesquad.autobid.kafka.adapter;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.kafka.producer.dto.AuctionKafkaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionOpenAdapter {

    @Value("${spring.kafka.topic.auction-send}")
    private String AUCTION_SEND_TOPIC_NAME;

    private final KafkaTemplate kafkaTemplate;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionRepository auctionRepository;
    private final ObjectMapper om;

    @KafkaListener(topics = "auction-open", groupId = "auction-open-consumer")
    public void consume(String json) throws JsonProcessingException {
        AuctionKafkaDTO auctionKafkaDTO = om.readValue(json, AuctionKafkaDTO.class);
        log.info("auction-open:{}", auctionKafkaDTO);
        // mysql
        Auction auction = auctionRepository.findById(auctionKafkaDTO.getAuctionId()).get();
        auction.open();
        auctionRepository.save(auction);
        log.info("redis save: {}", auction.getId());
        // redis
        auctionRedisRepository.save(AuctionRedisDTO.from(auction));

        produce(auctionKafkaDTO);
    }

    private void produce(AuctionKafkaDTO auctionKafkaDTO) throws JsonProcessingException {
        log.info("produce auction : {}", auctionKafkaDTO.getAuctionId());
        kafkaTemplate.send(AUCTION_SEND_TOPIC_NAME, new String(om.writeValueAsBytes(auctionKafkaDTO)));
    }
}
