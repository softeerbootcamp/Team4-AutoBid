package com.codesquad.autobid.auction.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionRedisKey {
    PRICE("auction#%d_price"),
    BIDDERS("auction#%d_bidders"),
    LOCK("auction#%d_lock");

    private String fieldName;
}
