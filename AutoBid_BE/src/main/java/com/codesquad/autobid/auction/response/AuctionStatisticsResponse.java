package com.codesquad.autobid.auction.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuctionStatisticsResponse {

    public static final Long FIXED_INTERVAL_PRICE = 500L;
    private int totalSold;
    private Long minPrice;
    private Long maxPrice;
    private StatisticsHistogram statisticsHistogram;

    private AuctionStatisticsResponse(int totalSold, Long minPrice, Long maxPrice,
                                      StatisticsHistogram statisticsHistogram) {
        this.totalSold = totalSold;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.statisticsHistogram = statisticsHistogram;
    }

    public static AuctionStatisticsResponse of(int totalSold, Long minPrice, Long maxPrice, int[] contents) {
        return new AuctionStatisticsResponse(totalSold, minPrice, maxPrice,
            new StatisticsHistogram((maxPrice - minPrice) / 20, contents));
    }
}

@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class StatisticsHistogram {

    private Long intervalPrice;
    private int[] contents;

    public StatisticsHistogram(Long intervalPrice, int[] contents) {
        this.intervalPrice = intervalPrice;
        this.contents = contents;
    }
}
