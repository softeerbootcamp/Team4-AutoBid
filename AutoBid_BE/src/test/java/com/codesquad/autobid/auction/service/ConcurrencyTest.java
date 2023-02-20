package com.codesquad.autobid.auction.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.redis.core.RedisTemplate;

import com.codesquad.autobid.auction.repository.AuctionRedisKey;
import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.AuctionRedisUtil;
import com.codesquad.autobid.bid.domain.Bid;

@SpringBootTest
public class ConcurrencyTest {

	@Autowired
	private AuctionRedisRepository auctionRedisRepository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@DisplayName("동시성 테스트")
	@Test
	public void test() throws InterruptedException {
		int threadNum = 500;
		Long auctionId = 999999L;
		Map<AuctionRedisKey, String> keys = AuctionRedisUtil.generateKeys(auctionId);
		redisTemplate.opsForValue().set(keys.get(AuctionRedisKey.PRICE), 1000);

		ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
		CountDownLatch countDownLatch = new CountDownLatch(threadNum);
		for (int i = 1; i <= threadNum; i++) {
			executorService.execute(() -> {
				boolean result = auctionRedisRepository.saveBid(Bid.of(AggregateReference.to(auctionId), AggregateReference.to(5L), 10000L, false));
				System.out.println(result);
				countDownLatch.countDown();
			});
		}

		countDownLatch.await();
		Long curPrice = Long.valueOf(String.valueOf(redisTemplate.opsForValue().get(keys.get(AuctionRedisKey.PRICE))));
		assertThat(curPrice).isEqualTo(10000L);

	}
}
