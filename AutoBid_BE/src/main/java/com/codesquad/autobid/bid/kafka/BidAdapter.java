package com.codesquad.autobid.bid.kafka;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.bid.domain.Bid;
import com.codesquad.autobid.bid.repository.BidRedisRepository;
import com.codesquad.autobid.bid.repository.BidRepository;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.service.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidAdapter {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final WebSocketService webSocketService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final BidRedisRepository bidRedisRepository;
    private final AuctionRedisRepository auctionRedisRepository;

    @KafkaListener(topics = "bid-event", groupId = "bid-mysql")
    public void saveBidAndUpdate(String bidRegisterRequestStr) throws JsonProcessingException {
        BidRegisterRequest bidRegisterRequest = objectMapper.readValue(bidRegisterRequestStr, BidRegisterRequest.class);
        log.info("bid-event bid-mysql {}", bidRegisterRequest);
        // bid 저장
        Bid bid = bidRepository.findBidByAuctionIdAndUserId(bidRegisterRequest.getAuctionId(),
                bidRegisterRequest.getUserId()).orElse(Bid.of(AggregateReference.to(bidRegisterRequest.getAuctionId()),
                AggregateReference.to(bidRegisterRequest.getUserId()), bidRegisterRequest.getSuggestedPrice(), false));

        bid.updatePrice(bidRegisterRequest.getSuggestedPrice());

        bidRepository.save(bid);

        // Auction 저장
        Auction auction = auctionRepository.findById(bidRegisterRequest.getAuctionId())
                .orElseThrow(() -> new RuntimeException("존재하는 경매가 없습니다."));

        auction.updateEndPrice(bidRegisterRequest.getSuggestedPrice());

        auctionRepository.save(auction);
    }

    @KafkaListener(topics = "bid-event", groupId = "bidder-redis")
    public void saveBiddersAndBroadcast(@Payload String bidRegisterRequestStr) throws JsonProcessingException {
        BidRegisterRequest bidRegisterRequest = objectMapper.readValue(bidRegisterRequestStr, BidRegisterRequest.class); // auctionID, userID
        Bid bid = Bid.of(AggregateReference.to(bidRegisterRequest.getAuctionId()),
                AggregateReference.to(bidRegisterRequest.getUserId()),
                bidRegisterRequest.getSuggestedPrice(), false);
        bidRedisRepository.save(bid); // redis 저장
        Long auctionId = bidRegisterRequest.getAuctionId();
        AuctionRedisDTO auctionRedis = auctionRedisRepository.findById(auctionId); // 저장된 것을 불러온다.
        AuctionDtoWebSocket auctionDtoWebSocket = webSocketService.parsingDto(auctionRedis); // 레디스에서 가져온 데이터를 파싱

        log.error("auctionRedis-id : {}", auctionRedis.getAuctionId());
        log.error("auctionRedis-price : {}", auctionRedis.getPrice());
        log.error("auctionDtoWebSocket-price : {}", auctionDtoWebSocket.getPrice());
        log.error("auctionDtoWebSocket-numberOfUsers : {}", auctionDtoWebSocket.getNumberOfUsers());
        messagingTemplate.convertAndSend("/ws/start/" + auctionId, auctionDtoWebSocket);
        log.info("bid-event bid-redis {}", bidRegisterRequest);
    }

    @KafkaListener(topics = "bid-event", groupId = "bidPrice-broadcast")
    public void broadcast(@Payload String bidRegisterRequestStr) throws JsonProcessingException {
        BidRegisterRequest bidRegisterRequest = objectMapper.readValue(bidRegisterRequestStr, BidRegisterRequest.class);
        log.info("bid-event bid-broadcast {}", bidRegisterRequest);
    }

    public void produce(BidRegisterRequest bidRegisterRequest) {
        try {
            log.info("produce {}", bidRegisterRequest);
            kafkaTemplate.send("bid-event", objectMapper.writeValueAsString(bidRegisterRequest));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
