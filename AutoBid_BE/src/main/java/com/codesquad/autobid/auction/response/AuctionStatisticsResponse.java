package com.codesquad.autobid.auction.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuctionStatisticsResponse {

	public static final Long FIXED_INTERVAL_PRICE = 500L;
	private Long totalSold;
	private Long minPrice;
	private Long maxPrice;
	private StatisticsHistogram statisticsHistogram;

	private AuctionStatisticsResponse(Long totalSold, Long minPrice, Long maxPrice, StatisticsHistogram statisticsHistogram) {
		this.totalSold = totalSold;
		this.maxPrice = maxPrice;
		this.minPrice = minPrice;
		this.statisticsHistogram = statisticsHistogram;
	}

	public static AuctionStatisticsResponse of(Long totalSold, Long minPrice, Long maxPrice, List<Long> contents) {
		return new AuctionStatisticsResponse(totalSold, minPrice, maxPrice,
			new StatisticsHistogram(FIXED_INTERVAL_PRICE, contents));
	}
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class StatisticsHistogram {

	private Long intervalPrice;
	private List<Long> contents;

	public StatisticsHistogram(Long intervalPrice, List<Long> contents) {
		this.intervalPrice = intervalPrice;
		this.contents = contents;
	}
}
