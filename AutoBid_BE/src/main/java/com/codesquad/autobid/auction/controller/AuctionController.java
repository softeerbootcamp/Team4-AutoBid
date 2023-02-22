package com.codesquad.autobid.auction.controller;

import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.auction.response.AuctionInfoListResponse;
import com.codesquad.autobid.auction.response.AuctionStatisticsResponse;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Auction", description = "경매 API")
@RestController
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private static final String NOT_FOUND_MSG = "fail";

    @Operation(summary = "경매 생성 API", description = "경매를 등록한다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void auctionAdd(
        @Parameter @ModelAttribute AuctionRegisterRequest auctionRegisterRequest,
        @Parameter(hidden = true) @AuthorizedUser User user
    ) {
        log.debug("POST /auction request : {}", auctionRegisterRequest);
        auctionService.addAuction(auctionRegisterRequest, user);
    }

    @Operation(summary = "경매 리스트 조회 API", description = "경매 리스트를 불러온다")
    @GetMapping("/list")
    public ResponseEntity<?> getAuctions(
        @Parameter(example = "ALL", description = "GN, EV, HEV, PHEV, FCEV, ALL 중에 하나") String carType,
        @Parameter(example = "ALL", description = "PROGRESS, BEFORE, COMPLETED, ALL 중에 하나") String auctionStatus,
        @Parameter(example = "1000", description = "범위 시작 가격") Long startPrice,
        @Parameter(example = "100000000", description = "범위 종료 가격") Long endPrice,
        @Parameter(example = "1", description = "페이지 번호") int page,
        @Parameter(example = "5", description = "한 페이지 크기, page: 1, size: 5일 경우 0,1,2,3,4번의 경매가 주어짐") int size
    ) {
        AuctionInfoListResponse auctionInfoListResponse = auctionService.getAuctions(carType, auctionStatus, startPrice, endPrice, page, size);
        if (auctionInfoListResponse.getTotalAuctionNum() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MSG);
        }
        return ResponseEntity.ok().body(auctionInfoListResponse);
    }

    @Operation(summary = "경매 통계 정보 조회 API", description = "경매 통계 정보를 불러온다")
    @GetMapping("/statistics")
    public ResponseEntity<?> getAuctionStatistics(
        @Parameter(example = "ALL", description = "GN, EV, HEV, PHEV, FCEV, ALL 중에 하나") String carType,
        @Parameter(example = "ALL", description = "PROGRESS, BEFORE, COMPLETED, ALL 중에 하나") String auctionStatus
    ) {
        AuctionStatisticsResponse auctionStatisticsResponse = auctionService.getAuctionStaticsResponse(carType, auctionStatus);
        if (auctionStatisticsResponse.getTotalSold() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");
        }

        return ResponseEntity.ok().body(auctionStatisticsResponse);
    }

    @Operation(summary = "내가 등록한 경매 리스트 조회 API", description = "내가 등록한 경매 리스트를 조회한다.")
    @GetMapping("/my")
    public ResponseEntity<?> getMyAuctions(@Parameter(hidden = true) @AuthorizedUser User user) {
        AuctionInfoListResponse auctionInfoListResponse = auctionService.getMyAuctions(user);
        if (auctionInfoListResponse.getTotalAuctionNum() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MSG);
        }
        return ResponseEntity.ok().body(auctionInfoListResponse);
    }

    @Operation(summary = "내가 참여한 경매 리스트 조회 API", description = "내가 참여한 경매 리스트를 조회한다.")
    @GetMapping("/my/participation")
    public ResponseEntity<?> getMyParticipationAuctions(@Parameter(hidden = true) @AuthorizedUser User user) {
        AuctionInfoListResponse auctionInfoListResponse = auctionService.getMyParticipatingAuctions(user);
        if (auctionInfoListResponse.getTotalAuctionNum() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MSG);
        }
        return ResponseEntity.ok().body(auctionInfoListResponse);
    }
}
