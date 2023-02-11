package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.AuctionStatus;
import org.springframework.data.repository.CrudRepository;

import com.codesquad.autobid.auction.domain.Auction;

import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {


    List<Auction> getAuctionByAuctionStatus(AuctionStatus auctionStatus);
}
