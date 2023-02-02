package com.codesquad.autobid.car.domain;

import java.util.Arrays;

/*
    경매중인지 아닌지 표현
    FOR_SALE : 경매에 등록된 상태
    NOT_FOR_SALE: 경매에 등록 안 된 상태
 */
public enum State {
    NOT_FOR_SALE(0),
    FOR_SALE(1);

    private int code;

    State(int code) {
        this.code = code;
    }

    public static State findByCode(int code) {
        return Arrays.stream(State.values())
                .filter(state -> state.code == code)
                .findAny()
                .orElse(NOT_FOR_SALE);
    }
}
