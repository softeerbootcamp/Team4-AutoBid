package com.codesquad.autobid.bid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.bid.service.BidService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class BidController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	private final BidService bidService;

	@Autowired
	public BidController(BidService bidService) {
		this.bidService = bidService;
	}

	@PostMapping("/auction/bid")
	public ResponseEntity<Boolean> bidRegister(@Parameter BidRegisterRequest bidRegisterRequest, @Parameter(hidden = true) @AuthorizedUser User user) {
		boolean result = bidService.suggestBid(bidRegisterRequest, user);

		messagingTemplate.convertAndSend("/subscribe/new/" + bidRegisterRequest.getAuctionId(), "bid");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
