package com.codesquad.autobid.auction;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.Distance;
import com.codesquad.autobid.car.domain.State;
import com.codesquad.autobid.car.domain.Type;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.handler.car.enums.DistanceUnit;
import com.codesquad.autobid.handler.car.vo.AvailableDistanceVO;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@SpringBootTest
public class AuctionRepositoryTest {

	@Autowired
	private CarRepository carRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuctionRepository auctionRepository;

	@Test
	public void 경매_입력_테스트() {
		// given
		User user = new User("uid", "email", "name", "mobile", LocalDate.now(), LocalDateTime.now(),
			LocalDateTime.now(), "refresh");

		user = userRepository.save(user);

		Car car = new Car(null, AggregateReference.to(user.getId()), State.FOR_SALE, Type.ETC, Distance.from(new AvailableDistanceVO(100L, 1)),
			"carid", "name", "sellname",
			LocalDateTime.now(), LocalDateTime.now());

		car = carRepository.save(car);

		// when
		Auction auction = Auction.of(car.getId(), user.getId(), LocalDateTime.now(), LocalDateTime.now(), 100L, 100L,
			AuctionStatus.BEFORE);

		Auction auctionData = auctionRepository.save(auction);

		// then
		assertThat(auction.getAuctionEndTime()).isEqualTo(auctionData.getAuctionEndTime());
		assertThat(auction.getAuctionStartTime()).isEqualTo(auctionData.getAuctionStartTime());
		assertThat(auction.getCarId()).isEqualTo(auctionData.getCarId());
		assertThat(auction.getUserId()).isEqualTo(auctionData.getUserId());
		assertThat(auction.getAuctionStartPrice()).isEqualTo(auctionData.getAuctionStartPrice());
		assertThat(auction.getAuctionEndPrice()).isEqualTo(auctionData.getAuctionEndPrice());
		assertThat(auction.getAuctionStatus()).isEqualTo(auctionData.getAuctionStatus());

	}

}
