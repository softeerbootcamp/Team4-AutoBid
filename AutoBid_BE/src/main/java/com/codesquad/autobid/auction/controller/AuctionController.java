package com.codesquad.autobid.auction.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

	public final AuctionService auctionService;

	@PostMapping()
	public void auctionAdd(@ModelAttribute AuctionRegisterRequest auctionRegisterRequest, @SessionAttribute(value = "user") User user) {
		auctionService.addAuction(auctionRegisterRequest, user);
	}

}
