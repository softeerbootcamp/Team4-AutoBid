package com.codesquad.autobid.auction.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionRedisKey {
    PRICE("auction#%d_price"),
    NUMBER_OF_USERS("auction#%d_number_of_users"),
    BIDDERS("auction#%d_bidders");

    private String fieldName;
}
