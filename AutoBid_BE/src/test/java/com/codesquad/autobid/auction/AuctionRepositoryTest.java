package com.codesquad.autobid.auction;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionInfoDto;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.auction.repository.AuctionRepository;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.State;
import com.codesquad.autobid.car.domain.Type;
import com.codesquad.autobid.car.repository.CarRepository;

@SpringBootTest
public class AuctionRepositoryTest {

	@Autowired
	private AuctionRepository auctionRepository;

	@Autowired
	private CarRepository carRepository;

	@DisplayName("필터 조회")
	@Test
	public void 필터_조회() {
		List<AuctionInfoDto> auctions = auctionRepository.findAllByFilter(100L, 100000L);
		Assertions.assertThat(auctions).isNotNull();
	}

}
