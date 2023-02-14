package com.codesquad.autobid.websocket.repository;

import com.codesquad.autobid.websocket.domain.WebSocketAuction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface WebSocketRepository extends CrudRepository<WebSocketAuction, Long> {

}
