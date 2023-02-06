package com.codesquad.autobid.auction.domain;

import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.user.domain.User;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Table("auction")
public class Auction {

	@Id
	@Column(value = "auction_id")
	private Long id;
	@Column(value = "car_id")
	private AggregateReference<Car, Long> carId;
	@Column(value = "user_id")
	private AggregateReference<User, Long> userId;
	@Column(value = "auction_start_time")
	private LocalDateTime auctionStartTime;
	@Column(value = "auction_end_time")
	private LocalDateTime auctionEndTime;
	@Column(value = "auction_start_price")
	private Long auctionStartPrice;
	@Column(value = "auction_end_price")
	private Long auctionEndPrice;
	@Column(value = "auction_status")
	private AuctionStatus auctionStatus;
	@CreatedDate
	@Column(value = "created_at")
	private LocalDateTime createdAt;
	@LastModifiedDate
	@Column(value = "updated_at")
	private LocalDateTime updatedAt;

	public Auction(Long carId, Long userId, LocalDateTime auctionStartTime, LocalDateTime auctionEndTime,
		Long auctionStartPrice,
		Long auctionEndPrice, AuctionStatus auctionStatus) {
		this.carId = AggregateReference.to(carId);
		this.userId = AggregateReference.to(userId);
		this.auctionStartTime = auctionStartTime;
		this.auctionEndTime = auctionEndTime;
		this.auctionStartPrice = auctionStartPrice;
		this.auctionEndPrice = auctionEndPrice;
		this.auctionStatus = auctionStatus;
	}
}