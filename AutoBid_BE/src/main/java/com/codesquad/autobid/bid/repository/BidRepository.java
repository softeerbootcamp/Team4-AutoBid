package com.codesquad.autobid.bid.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.codesquad.autobid.bid.domain.Bid;

public interface BidRepository extends CrudRepository<Bid, Long> {
	public Optional<Bid> findBidByAuctionIdAndUserId(Long auctionId, Long userId);
}
