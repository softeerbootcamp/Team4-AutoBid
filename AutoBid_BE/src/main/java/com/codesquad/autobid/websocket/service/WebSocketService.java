package com.codesquad.autobid.websocket.service;

import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.service.UserService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.AuctionUserWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import com.codesquad.autobid.websocket.repository.WebSocketAuctionUserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WebSocketService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserService userService;
    private final AuctionService auctionService;
    private final WebSocketService webSocketService;
    private final WebSocketAuctionUserRedisRepository webSocketAuctionUserRedisRepository;

    @Autowired
    public WebSocketService(SimpMessageSendingOperations messagingTemplate, UserService userService, AuctionService auctionService, WebSocketService webSocketService, WebSocketAuctionUserRedisRepository webSocketAuctionUserRedisRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.auctionService = auctionService;
        this.webSocketService = webSocketService;
        this.webSocketAuctionUserRedisRepository = webSocketAuctionUserRedisRepository;
    }

    public AuctionUserWebSocket auctionUserSave(Long auctionId, String session) { // 저장
        AuctionUserWebSocket auctionUserWebSocket = AuctionUserWebSocket.of(auctionId, session);
        webSocketAuctionUserRedisRepository.saveAuctionUser(auctionId, session);
        return auctionUserWebSocket;
    }

    public Long auctionBidUsersSize(Long auctionId) { // 유저 수
        return webSocketAuctionUserRedisRepository.countAuctionUsers(auctionId);
    }

    public void deleteAuctionAll(Long auctionId) { // 유저 모두 삭제
        webSocketAuctionUserRedisRepository.deleteAuctionUserAll(auctionId);
    }

    public List<BidderDto> bidderToBidderDto(Set<Bidder> bidders) {
        List<BidderDto> bidderDtoList = new ArrayList<>();
        BidderDto bidderDto = new BidderDto();
        for (Bidder i : bidders) {
            Long userId = i.getUserId();
            Optional<User> user = userService.findById(userId);
            if(user.isPresent()){
                bidderDto.setPrice(i.getPrice());
                bidderDto.setUserId(userId);
                bidderDto.setUserName(user.get().getName());
                bidderDto.setMobileNum(user.get().getMobilenum());
            }
        }
        bidderDtoList.add(bidderDto);
        return bidderDtoList;
    }

    public AuctionDtoWebSocket parsingDto(Long auctionId) {
        AuctionDtoWebSocket auctionDtoWebSocket = new AuctionDtoWebSocket();
        AuctionRedis auction = auctionService.getAuction(auctionId);
        List<BidderDto> bidderDtoList = new ArrayList<>();

        try {
            if (auction.getBidders().isEmpty()) {
                auctionDtoWebSocket = AuctionDtoWebSocket.of(0L, bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
            }
            else if (!auction.getBidders().isEmpty()) {
                bidderDtoList = webSocketService.bidderToBidderDto(auction.getBidders()); // bidder -> bidderDto
                auctionDtoWebSocket = AuctionDtoWebSocket.of(auction.getPrice(), bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
            }
        } catch (NullPointerException e) {

        }

        return auctionDtoWebSocket;
    }
}
