package com.codesquad.autobid.auction.repository;

import com.codesquad.autobid.auction.domain.Auction;
import com.codesquad.autobid.auction.domain.AuctionInfoDto;
import com.codesquad.autobid.auction.domain.AuctionStatus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {
    //@Query(value = "select * from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice order by a.auction_start_time desc")
    //public List<AuctionInfoDto> findAllByFilter(Long auctionStartPrice, Long auctionEndPrice);

    @Query(value = "select a.auction_title, a.auction_start_time, a.auction_id, a.auction_end_time,a.auction_start_price, a.auction_end_price, a.auction_status, a.user_id, a.car_id, c.car_distance, c.car_name, c.car_sellname, c.car_carid, c.car_type, c.car_state from (select * from auction where auction_end_price between :auctionStartPrice and :auctionEndPrice) a join car c on a.car_id = c.car_id")
    public List<AuctionInfoDto> findAllByFilter(Long auctionStartPrice, Long auctionEndPrice);

    @Query("select a.auction_title, a.auction_start_time, a.auction_id, a.auction_end_time,a.auction_start_price, a.auction_end_price, a.auction_status, a.user_id, a.car_id, c.car_distance, c.car_name, c.car_sellname, c.car_carid, c.car_type, c.car_state from (select * from auction where auction_end_price between :auctionStartPrice and :auctionEndPrice and auction_status = :auctionStatus) a join car c on a.car_id = c.car_id")
    public List<AuctionInfoDto> findAllByFilterWithAuctionStatus(Long auctionStartPrice, Long auctionEndPrice, String auctionStatus);

    //@Query("select a.auction_title, a.auction_start_time, a.auction_id, a.auction_end_time,a.auction_start_price, a.auction_end_price, a.auction_status, a.user_id, a.car_id, c.car_distance, c.car_name, c.car_sellname, c.car_carid, c.car_type, c.car_state from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice and c.car_type = :carType order by a.auction_start_time desc")
    @Query("select a.auction_title, a.auction_start_time, a.auction_id, a.auction_end_time,a.auction_start_price, a.auction_end_price, a.auction_status, a.user_id, a.car_id, c.car_distance, c.car_name, c.car_sellname, c.car_carid, c.car_type, c.car_state from (select * from auction where auction_end_price between :auctionStartPrice and :auctionEndPrice) a join car c on a.car_id = c.car_id where c.car_type = :carType")
    public List<AuctionInfoDto> findAllByFilterWithCarType(Long auctionStartPrice, Long auctionEndPrice, String carType);

    //@Query("select a.auction_title, a.auction_start_time, a.auction_id, a.auction_end_time,a.auction_start_price, a.auction_end_price, a.auction_status, a.user_id, a.car_id, c.car_distance, c.car_name, c.car_sellname, c.car_carid, c.car_type, c.car_state from auction a join car c on a.car_id = c.car_id where a.auction_end_price >= :auctionStartPrice and a.auction_end_price <= :auctionEndPrice and a.auction_status = :auctionStatus and c.car_type = :carType")
    @Query("select a.auction_title, a.auction_start_time, a.auction_id, a.auction_end_time,a.auction_start_price, a.auction_end_price, a.auction_status, a.user_id, a.car_id, c.car_distance, c.car_name, c.car_sellname, c.car_carid, c.car_type, c.car_state from (select * from auction where auction_end_price between :auctionStartPrice and :auctionEndPrice and auction_status = :auctionStatus) a join car c on a.car_id = c.car_id where c.car_type = :carType")
    public List<AuctionInfoDto> findAllByFilterWithAuctionStatusAndCarType(Long auctionStartPrice, Long auctionEndPrice, String auctionStatus, String carType);

    List<Auction> getAuctionByAuctionStatusAndAuctionStartTime(AuctionStatus auctionStatus, LocalDateTime startTime);

    List<Auction> getAuctionByAuctionStatusAndAuctionEndTime(AuctionStatus auctionStatus, LocalDateTime endTime);

    @Query("select auction_end_price from auction a join car c on a.car_id = c.car_id where a.auction_status = :auctionStatus and c.car_type = :carType order by a.auction_end_price asc")
    public List<Long> findAllByAuctionStatusAndCarType(String auctionStatus, String carType);

    @Query("select auction_end_price from auction where auction_status = :auctionStatus order by auction_end_price asc")
    public List<Long> findAllByAuctionStatus(String auctionStatus);

    //@Query("select auction_end_price from auction a join car c on a.car_id = c.car_id where c.car_type = :carType order by a.auction_end_price asc")
    @Query("select auction_end_price from (select auction_end_price, car_id from auction order by auction_end_price asc) a join car c on a.car_id = c.car_id where c.car_type = :carType order by auction_end_price asc")
    public List<Long> findAllByCarType(String carType);

    @Query("select auction_end_price from auction order by auction_end_price asc")
    public List<Long> findAllForStatistics();

    public int countAllByAuctionStatus(AuctionStatus auctionStatus);

    @Query("select * from auction a join car c on a.car_id = c.car_id where a.user_id = :userId order by a.auction_end_price asc")
    public List<AuctionInfoDto> findAllByUserId(Long userId);

    @Query("select * from auction a join car c on a.car_id = c.car_id where (a.auction_id in (select auction_id from bid where bid.user_id=:userId))")
    public List<AuctionInfoDto> findAllParticipatingAuctions(Long userId);
}
