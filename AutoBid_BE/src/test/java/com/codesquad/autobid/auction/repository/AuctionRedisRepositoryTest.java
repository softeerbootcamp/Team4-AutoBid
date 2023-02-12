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

@SpringBootTest
@Transactional
class AuctionRedisRepositoryTest {

    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AuctionRedisRepository auctionRedisRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;

    @Test
    @DisplayName("redis에 경매 데이터 저장")
    void save() {
        // given
        User user = UserTestUtil.getNewUser();
        userRepository.save(user);
        List<Car> cars = CarTestUtil.getNewCars(user.getId(), 1);
        carRepository.save(cars.get(0));
        Auction auction = Auction.of(cars.get(0).getId(), user.getId(), LocalDateTime.now(), LocalDateTime.now(), 100l, 200l, AuctionStatus.BEFORE);
        auction = auctionRepository.save(auction);
        // when
        auctionRedisRepository.save(AuctionRedis.from(auction));
        // then

    }
}