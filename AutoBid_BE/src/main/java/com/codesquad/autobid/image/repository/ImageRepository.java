package com.codesquad.autobid.image.repository;

import java.util.List;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.image.domain.Image;


public interface ImageRepository extends CrudRepository<Image, Long> {
	public List<Image> findAllByAuctionId(AggregateReference<Auction, Long> auctionId);

}
