package com.codesquad.autobid.auction.service;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRedisDTO;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.auction.request.AuctionRegisterRequest;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.util.CarTestUtil;
import com.codesquad.autobid.util.UserTestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuctionServiceTest {

    @Autowired
    public AuctionService auctionService;
    @Autowired
    public AuctionRepository auctionRepository;
    @Autowired
    public AuctionRedisRepository auctionRedisRepository;

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
        assertThat(auctionRepository.findById(auction.getId()).get()).usingRecursiveComparison().isEqualTo(auction);
    }

    private AuctionRegisterRequest getRequest(LocalDateTime startTime, User user) {
        AuctionRegisterRequest request = new AuctionRegisterRequest();
        request.setMultipartFileList(new ArrayList<>());
        Car car = CarTestUtil.saveCar(CarTestUtil.getNewCars(user.getId(), 1).get(0));
        request.setCarId(car.getId());
        request.setAuctionTitle("auction Title");
        request.setAuctionStartTime(startTime);
        request.setAuctionEndTime(startTime.plusMinutes(30));
        request.setAuctionStartPrice(1000l);
        return request;
    }

    @Test
    @DisplayName("시작시간이 도래한 경매를 전부 열기")
    void successOpenAuction() {
        // given
        User user = UserTestUtil.saveUser(UserTestUtil.getNewUser());
        List<Car> cars = CarTestUtil.getNewCars(user.getId(), 5)
            .stream()
            .map(CarTestUtil::saveCar)
            .collect(Collectors.toList());
        LocalDateTime startTime = LocalDateTime.now();
        List<Auction> pendingAuctions = generateAuctions(startTime, cars, AuctionStatus.BEFORE);
        auctionRepository.saveAll(pendingAuctions);
        try {
            // when
            auctionService.openPendingAuctions(startTime);
            // then
            assertThat(
                auctionRepository.getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus.PROGRESS, startTime).size()
            ).isEqualTo(pendingAuctions.size());
        } catch (JsonProcessingException e) {
        }
    }

    @Test
    @DisplayName("마감 시간이 도래한 경매를 전부 닫기")
    void successCloseAuction() {
        // given
        User user = UserTestUtil.saveUser(UserTestUtil.getNewUser());
        List<Car> cars = CarTestUtil.getNewCars(user.getId(), 5)
            .stream()
            .map(CarTestUtil::saveCar)
            .collect(Collectors.toList());
        LocalDateTime endTime = LocalDateTime.now();
        List<Auction> progressAuctions = generateAuctions(endTime, cars, AuctionStatus.PROGRESS);
        auctionRepository.saveAll(progressAuctions);
        for (Auction progressAuction : progressAuctions) {
            auctionRedisRepository.save(AuctionRedisDTO.from(progressAuction));
        }
        try {
            // when
            auctionService.closeFulfilledAuctions(endTime);
            // then
            assertThat(
                auctionRepository.getAuctionByAuctionStatusAndAuctionEndTime(AuctionStatus.COMPLETED, endTime).size()
            ).isEqualTo(progressAuctions.size());
        } catch (JsonProcessingException e) {
        }
    }

    private List<Auction> generateAuctions(LocalDateTime time, List<Car> cars, AuctionStatus status) {
        return cars.stream()
            .map(c -> Auction.of(c.getId(), c.getUserId().getId(), "title", time, time, 1l, 2l, status))
            .collect(Collectors.toList());
    }

    @Test
    @DisplayName("bid 시도")
    void tryBidding() {
        // given
        User user = UserTestUtil.getNewUser();
        UserTestUtil.saveUser(user);
        Long auctionId = 999999999l;
        Long startPrice = 100l;

        auctionRedisRepository.save(AuctionRedisDTO.of(auctionId, startPrice, List.of()));
        BidRegisterRequest bidRegisterRequest = new BidRegisterRequest();
        bidRegisterRequest.setAuctionId(auctionId);
        bidRegisterRequest.setUserId(user.getId());
        bidRegisterRequest.setSuggestedPrice(startPrice + 1);
        // when
        boolean hasSuccess = auctionService.saveBid(bidRegisterRequest);
        // then
        assertThat(hasSuccess).isTrue();
        auctionRedisRepository.deleteAuction(auctionId);
    }
}
