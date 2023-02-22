package com.codesquad.autobid.bid.controller;

import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.bid.adapter.BidAdapter;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BidController {
	private final BidAdapter bidAdapter;
	private final AuctionService auctionService;

	@Autowired
	public BidController(BidAdapter bidAdapter, AuctionService auctionService) {
		this.bidAdapter = bidAdapter;
		this.auctionService = auctionService;
	}

	@PostMapping("/auction/bid")
	public ResponseEntity<Boolean> bidRegister(@Parameter @RequestBody BidRegisterRequest bidRegisterRequest,
		@Parameter(hidden = true) @AuthorizedUser User user) throws JsonProcessingException {
		bidRegisterRequest.setUserId(user.getId());
		boolean result = auctionService.saveBidRedis(bidRegisterRequest);
		log.info(String.valueOf(result));
		if (!result) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		}
		//		 boolean result = bidService.suggestBid(bidRegisterRequest, user);
		//
		//		if (!result) {
		//		 	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		//		 }
		bidAdapter.produce(bidRegisterRequest);
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
}
