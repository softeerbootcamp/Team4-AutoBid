package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codesquad.autobid.util.AuctionTestUtil.saveAuction;
import static com.codesquad.autobid.util.CarTestUtil.getNewCars;
import static com.codesquad.autobid.util.CarTestUtil.saveCar;
import static com.codesquad.autobid.util.UserTestUtil.getNewUser;
import static com.codesquad.autobid.util.UserTestUtil.saveUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class AuctionRedisDTORepositoryTest {

    @Autowired
    private AuctionRedisRepository auctionRedisRepository;

    @Test
    @DisplayName("Redis에 경매 저장 성공")
    void saveSuccess() {
        // given
        User user = saveUser(getNewUser());
        Car car = saveCar(getNewCars(user.getId(), 1).get(0));
        Auction auction = saveAuction(user, car);
        AuctionRedisDTO auctionRedisDTO = AuctionRedisDTO.from(auction);
        // when
        auctionRedisRepository.save(auctionRedisDTO);
        // then
        AuctionRedisDTO findAuctionRedisDTO = auctionRedisRepository.findById(auctionRedisDTO.getAuctionId());
        assertAll(
                () -> assertThat(findAuctionRedisDTO.getAuctionRedisBidderDto().size()).isEqualTo(0),
                () -> assertThat(findAuctionRedisDTO).usingRecursiveComparison().isEqualTo(auctionRedisDTO)
        );
    }

    @Test
    @DisplayName("삭제 성공")
    void deleteSuccess() {
        // given
        User user = saveUser(getNewUser());
        Car car = saveCar(getNewCars(user.getId(), 1).get(0));
        Auction auction = saveAuction(user, car);
        AuctionRedisDTO auctionRedisDTO = AuctionRedisDTO.from(auction);
        // when
        auctionRedisRepository.save(auctionRedisDTO);
        auctionRedisRepository.delete(auction.getId());
        // then
    }
}
