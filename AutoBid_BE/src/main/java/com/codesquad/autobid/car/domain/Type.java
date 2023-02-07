package com.codesquad.autobid.car.domain;

public enum Type {
    GN("내연기관"),
    EV("전기"),
    HEV("하이브리드"),
    PHEV("플러그인하이브리드"),
    FCEV("수소전기"),
    ETC("그 외");

    private String description;

    Type(String description) {
        this.description = description;
    }
}
