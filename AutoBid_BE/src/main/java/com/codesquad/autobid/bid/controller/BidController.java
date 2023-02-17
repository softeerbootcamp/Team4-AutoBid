package com.codesquad.autobid.bid.controller;

import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import com.codesquad.autobid.websocket.service.WebSocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.bid.service.BidService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class BidController {
	private final SimpMessageSendingOperations messagingTemplate;
	private final BidService bidService;
	private final AuctionService auctionService;
	private final WebSocketService webSocketService;

	@Autowired
	public BidController(SimpMessageSendingOperations messagingTemplate,
						 BidService bidService,
						 AuctionService auctionService,
						 WebSocketService webSocketService) {
		this.messagingTemplate = messagingTemplate;
		this.bidService = bidService;
		this.auctionService = auctionService;
		this.webSocketService = webSocketService;
	}

	@PostMapping("/auction/bid")
	public ResponseEntity<Boolean> bidRegister(@Parameter @RequestBody BidRegisterRequest bidRegisterRequest,
											   @Parameter(hidden = true)
											   @AuthorizedUser User user) {
		boolean result = bidService.suggestBid(bidRegisterRequest, user);

		if (!result) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		}
//		log.info("bid : {}",bidRegisterRequest.getAuctionId());
//		log.info("price : {}",bidRegisterRequest.getSuggestedPrice());

//		Long auctionId = bidRegisterRequest.getAuctionId();
//		Long price = bidRegisterRequest.getSuggestedPrice();

//		AuctionRedis auction = auctionService.getAuction(auctionId);
//		List<BidderDto> bidderDtoList = new ArrayList<>();
//		AuctionDtoWebSocket auctionDtoWebSocket = new AuctionDtoWebSocket();
//
//		try {
//			if (auction.getBidders().isEmpty()) {
//				auctionDtoWebSocket = AuctionDtoWebSocket.of(price, bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
//			}
//			else if (!auction.getBidders().isEmpty()) {
//				bidderDtoList = webSocketService.bidderToBidderDto(auction.getBidders()); // bidder -> bidderDto
//				auctionDtoWebSocket = AuctionDtoWebSocket.of(auction.getPrice(), bidderDtoList); // 현재 입찰가, 입찰자들, 참여자 수
//			}
//		} catch (NullPointerException e) {
//
//		}
//
//		messagingTemplate.convertAndSend("/ws/start/" + auctionId, auctionDtoWebSocket);
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
}
