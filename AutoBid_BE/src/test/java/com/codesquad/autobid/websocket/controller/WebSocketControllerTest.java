package com.codesquad.autobid.websocket.controller;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedis;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.Bidder;
import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.bid.repository.BidRepository;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.websocket.domain.AuctionDtoWebSocket;
import com.codesquad.autobid.websocket.domain.BidderDto;
import com.codesquad.autobid.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codesquad.autobid.util.CarTestUtil.getNewCars;
import static com.codesquad.autobid.util.CarTestUtil.saveCar;
import static com.codesquad.autobid.util.UserTestUtil.getNewUser;
import static com.codesquad.autobid.util.UserTestUtil.saveUser;

@Slf4j
@Transactional
@SpringBootTest
class WebSocketControllerTest {

    private final AuctionService auctionService;
    private final WebSocketService webSocketService;
    private final AuctionRedisRepository auctionRedisRepository;
    private final BidRepository bidRepository;

    @Autowired
    WebSocketControllerTest(AuctionService auctionService,
                            WebSocketService webSocketService,
                            AuctionRedisRepository auctionRedisRepository,
                            BidRepository bidRepository) {
        this.auctionService = auctionService;
        this.webSocketService = webSocketService;
        this.auctionRedisRepository = auctionRedisRepository;
        this.bidRepository = bidRepository;
    }

    @DisplayName("Bidder 변환 확인")
    @Test
    void onAllEntered() {

        User user = saveUser(getNewUser());
        Car car = saveCar(getNewCars(user.getId(), 1).get(0));

        LocalDateTime now = LocalDateTime.now();

        // bidder
        Bidder bidder1 = new Bidder(1L,10000L);
        Bidder bidder2 = new Bidder(2L,20000L);
        Bidder bidder3 = new Bidder(3L,30000L);
        Bidder bidder4 = new Bidder(4L,40000L);

        Set<Bidder> bidderSet = new HashSet<>();
        bidderSet.add(bidder1);
        bidderSet.add(bidder2);
        bidderSet.add(bidder3);
        bidderSet.add(bidder4);

        // auction 저장
        Auction auction = Auction.of(car.getId(), user.getId(), "test", LocalDateTime.now(), LocalDateTime.now(),10000l, 50000l, AuctionStatus.PROGRESS);
        // bidder 등록
        AuctionRedis auctionRedis = AuctionRedis.of(auction.getId(), 10000L, bidderSet);
        // 레디스 저장
        auctionRedisRepository.save(auctionRedis);
        // 비더가져오기
        Set<Bidder> bidders = auctionRedis.getBidders();
        // set -> list
        List<BidderDto> bidderDtoList = webSocketService.bidderToBidderDto(bidders);

        int bidderSize = bidderDtoList.size();

        for (BidderDto i : bidderDtoList) {
            log.info("price: {}",  i.getPrice());
            log.info("mobileNum: {}",  i.getPhoneNumber());
            log.info("userName: {}",  i.getUsername());
        }

        AuctionDtoWebSocket auctionDtoWebSocket = AuctionDtoWebSocket.of(auctionRedis.getPrice(), bidderDtoList);
        log.info("현재입찰가: {}",  auctionDtoWebSocket.getPrice());
        List<BidderDto> biddersResult = auctionDtoWebSocket.getUsers();
        Long userNumber = auctionDtoWebSocket.getNumberOfUsers();

        Assertions.assertThat(biddersResult).isEqualTo(bidderDtoList);
        Assertions.assertThat(userNumber).isEqualTo(bidderSize);
    }
}
