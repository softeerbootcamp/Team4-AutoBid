package com.codesquad.autobid.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Auction", description = "경매 API")
@RestController
@RequestMapping("/auction")
public class AuctionController {

	private final AuctionService auctionService;

	@Autowired
	public AuctionController(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	@Operation(summary = "경매 생성 API", description = "경매를 등록한다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void auctionAdd(@Parameter @ModelAttribute AuctionRegisterRequest auctionRegisterRequest,
		@Parameter(hidden = true) @AuthorizedUser User user) {

		log.debug("POST /auction request : {}", auctionRegisterRequest);
		auctionService.addAuction(auctionRegisterRequest, user);
	}

}
