package com.codesquad.autobid.auction.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AuctionRedisUtil {

    public static Map<AuctionRedisKey, String> generateKeys(Long auctionId) {
        HashMap<AuctionRedisKey, String> keys = new HashMap<>();
        Arrays.stream(AuctionRedisKey.values())
                .forEach(k -> keys.put(k, String.format(k.getFieldName(), auctionId)));
        return keys;
    }
}
