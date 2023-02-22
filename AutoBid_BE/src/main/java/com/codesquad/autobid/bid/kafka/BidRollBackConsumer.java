package com.codesquad.autobid.bid.kafka;

import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.bid.repository.BidRepository;
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

    private static final Long BIDDING_DIFFERENCE = 5l;

    private final ObjectMapper om;
    private final AuctionRedisRepository auctionRedisRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    @KafkaListener(topics = "redis-rollback", groupId = "redis-rollback-consumer")
    public void consume(String json) throws JsonProcessingException {
        BidRegisterRequest bidRegisterRequest = om.readValue(json, BidRegisterRequest.class);
        Long auctionId = bidRegisterRequest.getAuctionId();
        Long userId = bidRegisterRequest.getUserId();
        auctionRedisRepository.deleteBidder(auctionId, userId);
        auctionRepository.updateEndPrice(auctionId, bidRegisterRequest.getSuggestedPrice() - BIDDING_DIFFERENCE);
        bidRepository.deleteByAuctionIdAndUserId(auctionId, userId);
    }
}
