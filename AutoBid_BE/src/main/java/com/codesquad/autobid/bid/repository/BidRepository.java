package com.codesquad.autobid.bid.repository;

import org.springframework.data.repository.CrudRepository;

import com.codesquad.autobid.bid.domain.Bid;

public interface BidRepository extends CrudRepository<Bid, Long> {
}
