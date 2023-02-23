package com.codesquad.autobid.bid.repository;

import com.codesquad.autobid.bid.domain.Bid;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid, Long> {

    public Optional<Bid> findBidByAuctionIdAndUserId(Long auctionId, Long userId);

    public void deleteByAuctionIdAndUserId(Long auctionId, Long userId);
}
