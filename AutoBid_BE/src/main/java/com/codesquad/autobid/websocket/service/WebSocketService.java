package com.codesquad.autobid.websocket.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRedisBidderDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.service.UserService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserService userService;

    public List<BidderDto> bidderToBidderDto(List<AuctionRedisBidderDTO> auctionRedisBidderDTOS) {
        return auctionRedisBidderDTOS.stream().map(auctionRedisBidderDTO -> {
            User user = userService.findById(auctionRedisBidderDTO.getUserId()).get();
            return BidderDto.of(
                    auctionRedisBidderDTO.getUserId(),
                    auctionRedisBidderDTO.getPrice(),
                    user.getName(),
                    user.getMobilenum()
            );
        }).collect(Collectors.toList());
    }

    public AuctionDtoWebSocket parsingDto(AuctionRedisDTO auctionRedis) {
        return AuctionDtoWebSocket.of(auctionRedis.getPrice(), bidderToBidderDto(auctionRedis.getAuctionRedisBidderDto()));
    }

    public AuctionDtoWebSocket parsingDto(Auction auction) {
        // todo: use user, bid repository and return user information
        List<BidderDto> bidderDtoList = new ArrayList<>();
        return AuctionDtoWebSocket.of(auction.getAuctionEndPrice(), bidderDtoList);
    }

    public void broadCast(AuctionDtoWebSocket auctionDtoWebSocket, String url) {
        // /end/auctionId
        messagingTemplate.convertAndSend("/ws" + url, auctionDtoWebSocket);
    }
}
