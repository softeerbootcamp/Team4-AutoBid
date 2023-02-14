package com.codesquad.autobid.car.service;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.bid.service.BidService;
import com.codesquad.autobid.test.UserTestUtil;

@Transactional
@SpringBootTest
public class BidServiceTest {

	@Autowired
	private BidService bidService;

	@DisplayName("입찰하기")
	@Test
	@Disabled
	public void suggestBidTest() {
		// given
		BidRegisterRequest bidRegisterRequest = new BidRegisterRequest(5L, 1000L);

		// when
		boolean result = bidService.suggestBid(bidRegisterRequest, UserTestUtil.TEST_USER_1);

		// then
		assertThat(result).isEqualTo(true);
	}
}
