package com.codesquad.autobid.util;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.user.domain.User;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class AuctionTestUtil {

    public static Auction saveAuction(User user, Car car) {
        try {
            Auction auction = Auction.of(car.getId(), user.getId(), "test", LocalDateTime.now(), LocalDateTime.now(),1l, 2l, AuctionStatus.PROGRESS);
            Field id = auction.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(auction, 1l);
            return auction;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
