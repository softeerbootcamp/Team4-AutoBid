package com.codesquad.autobid.auction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codesquad.autobid.auction.service.AuctionService;
import com.codesquad.autobid.user.repository.UserRepository;

@SpringBootTest
public class AuctionServiceTest {

	@Autowired
	private AuctionService auctionService;
	@Autowired
	private UserRepository userRepository;

	@DisplayName("리스트 조회")
	@Test
	public void 리스트_조회() {
		System.out.println(auctionService.getAuctions("EV", "BEFORE", 0L, 1000000L, 1, 5));
		Assertions.assertThat(auctionService.getAuctions("ETC", "BEFORE", 1000L, 1000000L, 1, 5)).isNotNull();
	}

	@DisplayName("통계 조회")
	@Test
	public void 통계_조회() {
		Assertions.assertThat(auctionService.getAuctionStaticsResponse("ALL", "ALL")).isNotNull();
	}

	@DisplayName("내가 등록한 경매 조회")
	@Test
	public void 내가_등록한_경매_조회() {
		Assertions.assertThat(auctionService.getMyAuctions(userRepository.findById(1L).get())).isNotNull();
	}

	@DisplayName("내가 참여한 경매 조회")
	@Test
	public void 내가_참여한_경매_조회() {
		System.out.println(auctionService.getMyParticipatingAuctions(userRepository.findById(10L).get()));
		Assertions.assertThat(auctionService.getMyParticipatingAuctions(userRepository.findById(1L).get())).isNotNull();
	}

}
