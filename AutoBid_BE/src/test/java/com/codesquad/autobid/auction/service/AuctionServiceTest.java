package com.codesquad.autobid.auction.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.util.CarTestUtil;
import com.codesquad.autobid.util.UserTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
@Transactional
class AuctionServiceTest {

    @Autowired
    public AuctionService auctionService;
    @Autowired
    public AuctionRepository auctionRepository;

    @Test
    @DisplayName("경매 등록 성공")
    void successAddAuction() {
        // given
        LocalDateTime startTime = LocalDateTime.now();
        User user = UserTestUtil.saveUser(UserTestUtil.getNewUser());
        AuctionRegisterRequest request = getRequest(startTime, user);
        // when
        Auction auction = auctionService.addAuction(request, user);
        // then
        Assertions.assertThat(auctionRepository.findById(auction.getId()).get()).usingRecursiveComparison().isEqualTo(auction);
    }

    private AuctionRegisterRequest getRequest(LocalDateTime startTime, User user) {
        AuctionRegisterRequest request = new AuctionRegisterRequest();
        request.setMultipartFileList(new ArrayList<>());
        Car car = CarTestUtil.saveCar(user);
        request.setCarId(car.getId());
        request.setAuctionStartTime(startTime);
        request.setAuctionEndTime(startTime.plusMinutes(30));
        request.setAuctionStartPrice(1000l);
        return request;
    }
}
