package com.codesquad.autobid.auction.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Auction", description = "경매 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

	public final AuctionService auctionService;

	@Operation(summary = "경매 생성 API", description = "경매를 등록한다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void auctionAdd(@Parameter @ModelAttribute AuctionRegisterRequest auctionRegisterRequest,
		@Parameter(hidden = true) @AuthorizedUser User user) {

		log.debug("POST /auction request : {}", auctionRegisterRequest);
		auctionService.addAuction(auctionRegisterRequest, user);
	}

}
