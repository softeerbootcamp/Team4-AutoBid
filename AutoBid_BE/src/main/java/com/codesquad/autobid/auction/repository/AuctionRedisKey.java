package com.codesquad.autobid.auction.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum AuctionRedisKey {
    PRICE("auction#%d_price"),
    BIDDERS("auction#%d_bidders"),
    LOCK("auction#%d_lock");

    private String fieldName;

    public static Map<AuctionRedisKey, String> generate(Long id) {
        HashMap<AuctionRedisKey, String> keys = new HashMap<>();
        Arrays.stream(AuctionRedisKey.values())
            .forEach(k -> keys.put(k, String.format(k.getFieldName(), id)));
        return keys;
    }
}
