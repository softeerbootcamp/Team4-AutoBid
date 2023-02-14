package com.codesquad.autobid.websocket.domain;

import lombok.Getter;

@Getter
public class WebSocketRedis {
    private String session;

    public WebSocketRedis(String session){
        this.session = session;
    }

    public static WebSocketRedis from(String session) {
        return new WebSocketRedis(session);
    }
}
