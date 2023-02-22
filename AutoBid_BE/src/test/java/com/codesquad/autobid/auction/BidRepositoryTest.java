package com.codesquad.autobid.auction;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.autobid.bid.domain.Bid;
import com.codesquad.autobid.bid.repository.BidRepository;

@Transactional
@SpringBootTest
public class BidRepositoryTest {

	@Autowired
	private BidRepository bidRepository;

	@DisplayName("Bid insert 확인")
	@Test
	public void bidInsertTest() {
		Bid bid = Bid.of(AggregateReference.to(5L), AggregateReference.to(5L), 10000L, false);
		bidRepository.save(bid);
		assertThat(bid.getPrice()).isEqualTo(10000L);
	}

	@DisplayName("bid find test")
	@Test
	public void bidFind() {
		Optional<Bid> bid = bidRepository.findBidByAuctionIdAndUserId(5L, 5L);
		if (bid.isPresent()) {
			System.out.println(bid);
		}
	}
}
