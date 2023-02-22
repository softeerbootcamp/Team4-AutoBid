package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionInfoDto;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {
    @Query(value = "select * from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice order by a.auction_start_time desc")
    public List<AuctionInfoDto> findAllByFilter(Long auctionStartPrice, Long auctionEndPrice);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice and a.auction_status = :auctionStatus order by a.auction_start_time desc")
    public List<AuctionInfoDto> findAllByFilterWithAuctionStatus(Long auctionStartPrice, Long auctionEndPrice, String auctionStatus);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice and c.car_type = :carType order by a.auction_start_time desc")
    public List<AuctionInfoDto> findAllByFilterWithCarType(Long auctionStartPrice, Long auctionEndPrice, String carType);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice and a.auction_status = :auctionStatus and c.car_type = :carType order by a.auction_start_time desc")
    public List<AuctionInfoDto> findAllByFilterWithAuctionStatusAndCarType(Long auctionStartPrice, Long auctionEndPrice, String auctionStatus, String carType);

    List<Auction> getAuctionByAuctionStatusAndAuctionStartTimeLessThanEqual(AuctionStatus auctionStatus, LocalDateTime startTime);

    List<Auction> getAuctionByAuctionStatusAndAuctionEndTimeLessThanEqual(AuctionStatus auctionStatus, LocalDateTime endTime);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.auction_status = :auctionStatus and c.car_type = :carType order by a.auction_end_price asc")
    public List<AuctionInfoDto> findAllByAuctionStatusAndCarType(String auctionStatus, String carType);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.auction_status = :auctionStatus order by a.auction_end_price asc")
    public List<AuctionInfoDto> findAllByAuctionStatus(String auctionStatus);

    @Query("select * from auction a join car c on a.car_id = c.car_id where c.car_type = :carType order by a.auction_end_price asc")
    public List<AuctionInfoDto> findAllByCarType(String carType);

    @Query("select * from auction a join car c on a.car_id = c.car_id order by a.auction_end_price asc")
    public List<AuctionInfoDto> findAllForStatistics();

    public int countAllByAuctionStatus(AuctionStatus auctionStatus);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.user_id = :userId order by a.auction_end_price asc")
    public List<AuctionInfoDto> findAllByUserId(Long userId);

    @Query("select * from auction a join car c on a.car_id = c.car_id where (a.auction_id in (select auction_id from bid where bid.user_id=:userId))")
    public List<AuctionInfoDto> findAllParticipatingAuctions(Long userId);
}
