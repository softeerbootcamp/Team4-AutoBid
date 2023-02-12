package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {


    List<Auction> getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus auctionStatus, LocalDateTime startTime);

    List<Auction> getAuctionByAuctionStatusAndAuctionEndTime(AuctionStatus auctionStatus, LocalDateTime endTime);
}
