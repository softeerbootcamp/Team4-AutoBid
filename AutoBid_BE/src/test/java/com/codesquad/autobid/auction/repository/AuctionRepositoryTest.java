package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.car.util.CarTestUtil;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
import com.codesquad.autobid.user.util.UserTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class AuctionRepositoryTest {

    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CarRepository carRepository;

    @Test
    @DisplayName("시작 날짜, 상태로 경매 찾기")
    void findByStartDateAndStatus() {
        // given
        User user = UserTestUtil.getNewUser();
        userRepository.save(user);
        Car car = CarTestUtil.getNewCars(user.getId(), 1).get(0);
        carRepository.save(car);

        LocalDateTime now = LocalDateTime.now();
        Auction auction = Auction.of(car.getId(), user.getId(),  now, now, 1l, 2l, AuctionStatus.BEFORE);
        auctionRepository.save(auction);
        // when
        List<Auction> findAuctions = auctionRepository.getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus.BEFORE, now);
        // then
        assertAll(
                () -> assertThat(findAuctions.size()).isEqualTo(1),
                () -> assertThat(findAuctions.get(0)).usingRecursiveComparison().isEqualTo(auction)
        );
    }
}
