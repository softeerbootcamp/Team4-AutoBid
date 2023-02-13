package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.util.CarTestUtil;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.util.UserTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class AuctionRedisRepositoryTest {

    @Autowired
    private AuctionRedisRepository auctionRedisRepository;

    @Test
    @DisplayName("Redis에 경매 저장 성공")
    void saveSuccess() {
        // given
        User user = saveUser();
        Car car = saveCar(user);
        Auction auction = saveAuction(user, car);

        AuctionRedis auctionRedis = AuctionRedis.from(auction);
        auctionRedis.addBidder(new Bidder(1l, 1000l));
        auctionRedis.addBidder(new Bidder(2l, 2000l));
        auctionRedis.addBidder(new Bidder(3l, 3000l));
        // when
        auctionRedisRepository.save(auctionRedis);
        // then
        AuctionRedis findAuctionRedis = auctionRedisRepository.findById(auctionRedis.getId()).get();
        assertAll(
                () -> assertThat(findAuctionRedis.getBidders().size()).isEqualTo(3),
                () -> assertThat(findAuctionRedis).usingRecursiveComparison().isEqualTo(auctionRedis)
        );
    }

    private Auction saveAuction(User user, Car car) {
        try {
            Auction auction = Auction.of(car.getId(), user.getId(), LocalDateTime.now(), LocalDateTime.now(), 1l, 2l, AuctionStatus.PROGRESS);
            Field id = auction.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(auction, 1l);
            return auction;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    private Car saveCar(User user) {
        try {
            Car car = CarTestUtil.getNewCars(user.getId(), 1).get(0);
            Field id = car.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(car, 1l);
            return car;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    private User saveUser() {
        try {
            User user = UserTestUtil.getNewUser();
            Field id = user.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(user, 1l);
            return user;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    @Test
    @DisplayName("삭제 성공")
    void deleteSuccess() {

    }
}
