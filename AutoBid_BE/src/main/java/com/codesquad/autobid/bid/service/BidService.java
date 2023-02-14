package com.codesquad.autobid.bid.service;

import com.codesquad.autobid.auction.repository.AuctionRedisRepository;
import com.codesquad.autobid.auction.repository.exceptions.AuctionNotFoundException;
import com.codesquad.autobid.bid.domain.Bid;
import com.codesquad.autobid.bid.repository.BidRedisRepository;
import com.codesquad.autobid.bid.repository.BidRepository;
import com.codesquad.autobid.bid.request.BidRegisterRequest;
import com.codesquad.autobid.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BidService {

    private final BidRepository bidRepository;

    private final BidRedisRepository bidRedisRepository;
    private final AuctionRedisRepository auctionRedisRepository;

    @Autowired
    public BidService(BidRepository bidRepository, BidRedisRepository bidRedisRepository, AuctionRedisRepository auctionRedisRepository) {
        this.bidRepository = bidRepository;
        this.bidRedisRepository = bidRedisRepository;
        this.auctionRedisRepository = auctionRedisRepository;
    }

    @Transactional
    public Boolean suggestBid(BidRegisterRequest bidRegisterRequest, User user) {
        try {
            if (!checkBid(bidRegisterRequest.getSuggestedPrice(), bidRegisterRequest.getAuctionId(), user)) {
                return false;
            }
            Bid bid = Bid.of(
                    AggregateReference.to(bidRegisterRequest.getAuctionId()),
                    AggregateReference.to(user.getId()),
                    bidRegisterRequest.getSuggestedPrice(),
                    false);

            bidRepository.save(bid);
            bidRedisRepository.save(bid);
            return true;
        } catch (AuctionNotFoundException e) {
            return false; // todo: return error response
        }
    }

    // 입찰 가능한지 확인
    // 동시성 해결 예정
    public Boolean checkBid(Long price, Long auctionId, User User) throws AuctionNotFoundException {
        return auctionRedisRepository.getPrice(auctionId) < price;
    }
}

