package com.codesquad.autobid.car.domain;

public enum Category {

    COMPACT(0),
    MIDSIZE(1),
    TRUCK(2),
    SPORT(3),
    SUV(4),
    ETC(5);

    private int id;

    Category(int id) {
        this.id = id;
    }
}
