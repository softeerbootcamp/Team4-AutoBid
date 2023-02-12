package com.codesquad.autobid.auction.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionRedisKey {

    PRICE("auction#%d_price"),
    USERS("auction#%d_users"),
    TOP_5_USERS("auction#%d_top_5_users"),
    START_TIME("auction#%d_start_time"),
    END_TIME("auction#%d_end_time"),
    BINS("auction#%d_bins");

    private String fieldName;
}
