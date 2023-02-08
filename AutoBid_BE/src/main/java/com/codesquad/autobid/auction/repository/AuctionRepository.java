package com.codesquad.autobid.auction.repository;

import org.springframework.data.repository.CrudRepository;

import com.codesquad.autobid.auction.domain.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Long> {
}
