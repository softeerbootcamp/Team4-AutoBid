package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.util.CarTestUtil;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.util.UserTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static com.codesquad.autobid.util.AuctionTestUtil.saveAuction;
import static com.codesquad.autobid.util.CarTestUtil.saveCar;
import static com.codesquad.autobid.util.UserTestUtil.getNewUser;
import static com.codesquad.autobid.util.UserTestUtil.saveUser;
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
        User user = saveUser(getNewUser());
        Car car = saveCar(user);
        Auction auction = saveAuction(user, car);
        AuctionRedis auctionRedis = AuctionRedis.from(auction);
        // when
        auctionRedisRepository.save(auctionRedis);
        // then
        AuctionRedis findAuctionRedis = auctionRedisRepository.findById(auctionRedis.getId());
        assertAll(
                () -> assertThat(findAuctionRedis.getBidders().size()).isEqualTo(0),
                () -> assertThat(findAuctionRedis).usingRecursiveComparison().isEqualTo(auctionRedis)
        );
    }

    @Test
    @DisplayName("삭제 성공")
    void deleteSuccess() {
        // given
        User user = saveUser(getNewUser());
        Car car = saveCar(user);
        Auction auction = saveAuction(user, car);
        AuctionRedis auctionRedis = AuctionRedis.from(auction);
        // when
        auctionRedisRepository.save(auctionRedis);
        auctionRedisRepository.delete(auction);
        // then
    }
}
