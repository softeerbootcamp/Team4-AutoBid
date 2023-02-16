package com.codesquad.autobid.websocket.domain;

import java.security.Principal;

public class StompPrincipal implements Principal {

    String session;

    public StompPrincipal(String session) {
        this.session = session;
    }

    @Override
    public String getName() {
        return session;
    }
}
