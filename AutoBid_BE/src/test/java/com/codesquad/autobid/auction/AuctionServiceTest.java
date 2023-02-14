package com.codesquad.autobid.auction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codesquad.autobid.auction.service.AuctionService;

@SpringBootTest
public class AuctionServiceTest {

	@Autowired
	private AuctionService auctionService;

	@DisplayName("리스트 조회")
	@Test
	public void 리스트_조회() {
		Assertions.assertThat(auctionService.getAuctions("ETC", "BEFORE", 1000L, 1000000L, 1, 5)).isNotNull();
	}
}
