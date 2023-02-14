package com.codesquad.autobid.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebSocketRedisKey {
    SESSION("session#%d_session");
    private String fieldname;
}
