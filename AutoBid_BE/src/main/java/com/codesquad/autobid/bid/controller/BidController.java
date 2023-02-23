package com.codesquad.autobid.bid.controller;

import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BidController {

    private final AuctionService auctionService;

    @PostMapping("/auction/bid")
    public ResponseEntity<Boolean> bidRegister(
            @Parameter @RequestBody BidRegisterRequest bidRegisterRequest,
            @Parameter(hidden = true) @AuthorizedUser User user
    ) {
        log.info("BidController-bidRegister: {}", bidRegisterRequest);
        boolean result;
        try {
            bidRegisterRequest.setUserId(user.getId());
            result = auctionService.saveBid(bidRegisterRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        if (result == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
