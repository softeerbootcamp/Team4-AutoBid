package com.codesquad.autobid.bid;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Table("bid")
public class Bid {

	@Column("bid_id")
	private Long id;
	@Column("auction_id")
	private AggregateReference<Auction, Long> auctionId;
	@Column("user_id")
	private AggregateReference<User, Long> userId;
	@Column("bid_accept")
	private Boolean bidAccept;
	@CreatedDate
	@Column("created_at")
	private LocalDateTime createdAt;
	@LastModifiedDate
	@Column("updated_at")
	private LocalDateTime updatedAt;

	private Bid(AggregateReference<Auction, Long> auctionId, AggregateReference<User, Long> userId, Boolean bidAccept) {
		this.auctionId = auctionId;
		this.userId = userId;
		this.bidAccept = bidAccept;
	}

	public static Bid of(AggregateReference<Auction, Long> auctionId, AggregateReference<User, Long> userId,
		Boolean bidAccept) {
		return new Bid(auctionId, userId, bidAccept);
	}

}
