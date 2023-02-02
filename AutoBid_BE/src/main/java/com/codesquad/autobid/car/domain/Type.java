package com.codesquad.autobid.car.domain;

import java.util.Optional;

public enum Type {
    GN("내연기관"),
    EV("전기"),
    HEV("하이브리드"),
    PHEV("플러그인하이브리드"),
    FCEV("수소전기");

    private String description;

    Type(String description) {
        this.description = description;
    }

    public static Type findByDescription(String description) {
        return Type.valueOf(description);
    }
}
