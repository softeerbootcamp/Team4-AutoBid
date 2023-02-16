package com.codesquad.autobid.websocket.service;

import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.service.UserService;
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
    private final WebSocketAuctionUserRedisRepository webSocketAuctionUserRedisRepository;

    @Autowired
    public WebSocketService(SimpMessageSendingOperations messagingTemplate, UserService userService, WebSocketAuctionUserRedisRepository webSocketAuctionUserRedisRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
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

    public void exitAuctionWebsocketMessage() {
//        messagingTemplate
    }

}
