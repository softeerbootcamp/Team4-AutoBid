package com.codesquad.autobid.kafka.consumer;

import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidRollBackConsumer {

    private final ObjectMapper om;
    private final AuctionRedisRepository auctionRedisRepository;

    @KafkaListener(topics = "redis-rollback", groupId = "redis-rollback-consumer")
    public void consume(String json) throws JsonProcessingException {
        BidRegisterRequest bidRegisterRequest = om.readValue(json, BidRegisterRequest.class);
        auctionRedisRepository.deleteAuction(bidRegisterRequest.getAuctionId());
    }
}
