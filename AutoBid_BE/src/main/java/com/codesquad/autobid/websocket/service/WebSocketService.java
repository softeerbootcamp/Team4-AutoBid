package com.codesquad.autobid.websocket.service;

import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisBidderDTO;
import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.service.UserService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WebSocketService {
    private final UserService userService;
    private final AuctionService auctionService;

    @Autowired
    public WebSocketService(UserService userService, AuctionService auctionService) {
        this.userService = userService;
        this.auctionService = auctionService;
    }

    public List<BidderDto> bidderToBidderDto(Set<AuctionRedisBidderDTO> auctionRedisBidderDTOS) {
        List<BidderDto> bidderDtoList = new ArrayList<>();
        BidderDto bidderDto = new BidderDto();
        for (AuctionRedisBidderDTO i : auctionRedisBidderDTOS) {
            Long userId = i.getUserId();
            Optional<User> user = userService.findById(userId);
            if(user.isPresent()){
                bidderDto.setPrice(i.getPrice());
                bidderDto.setUserId(userId);
                bidderDto.setUsername(user.get().getName());
                bidderDto.setPhoneNumber(user.get().getMobilenum());
            }
            bidderDtoList.add(bidderDto);
        }
        return bidderDtoList;
    }

    public AuctionDtoWebSocket parsingDto(AuctionRedisDTO auctionRedis) {
        AuctionDtoWebSocket auctionDtoWebSocket = new AuctionDtoWebSocket();
        AuctionRedisDTO auction = auctionService.getAuction(auctionRedis.getAuctionId());
        List<BidderDto> bidderDtoList = new ArrayList<>();

        try {
            if (auction.getAuctionRedisBidderDto().isEmpty()) {
                auctionDtoWebSocket = AuctionDtoWebSocket.of(0L, bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
            }
            else if (!auction.getAuctionRedisBidderDto().isEmpty()) {
                bidderDtoList = bidderToBidderDto(auction.getAuctionRedisBidderDto()); // bidder -> bidderDto
                auctionDtoWebSocket = AuctionDtoWebSocket.of(auction.getPrice(), bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
            }
        } catch (NullPointerException e) {

        }

        return auctionDtoWebSocket;
    }

    public AuctionDtoWebSocket parsingDto(Auction auction) {
        List<BidderDto> bidderDtoList = new ArrayList<>();
        return AuctionDtoWebSocket.of(auction.getAuctionEndPrice(), bidderDtoList);
    }
}
