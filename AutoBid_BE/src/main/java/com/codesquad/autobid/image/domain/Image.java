package com.codesquad.autobid.image.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import com.codesquad.autobid.auction.domain.Auction;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("image")
public class Image {

	@Id
	@Column(value = "image_id")
	private Long id;
	@Column(value = "auction_id")
	private AggregateReference<Auction, Long> auctionId;
	@Column(value = "image_url")
	private String imageUrl;

	private Image(Long auctionId, String imageUrl) {
		this.auctionId = AggregateReference.to(auctionId);
		this.imageUrl = imageUrl;
	}

	public static Image of(Long auctionId, String imageUrl) {
		return new Image(auctionId, imageUrl);
	}
}
