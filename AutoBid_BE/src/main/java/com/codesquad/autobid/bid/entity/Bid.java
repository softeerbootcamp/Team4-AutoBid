package com.codesquad.autobid.bid.entity;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("bid")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bid {

    @Id
    @Column("bid_Id")
    private Long id;

    @Column("auction_id")
    private AggregateReference<Auction, Long> auctionId;

    @Column("user_id")
    private AggregateReference<User, Long> userId;

    @Column("bid_accept")
    private Boolean isAccepted;

    @Column("bid_price")
    private Integer price;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public static Bid from(Long auctionId, Long userId, Integer price) {
        Bid bid = new Bid();
        bid.auctionId = AggregateReference.to(auctionId);
        bid.userId = AggregateReference.to(userId);
        bid.isAccepted = false;
        bid.price = price;
        bid.created_at = LocalDateTime.now();
        bid.updated_at = LocalDateTime.now();
        return bid;
    }
}
