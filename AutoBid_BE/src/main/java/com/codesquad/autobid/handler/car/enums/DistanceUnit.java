package com.codesquad.autobid.handler.car.enums;

import java.util.Arrays;

public enum DistanceUnit {

    FEET(0, "feet"),
    KM(1, "KM"),
    METER(2, "METER"),
    MILE(3, "MILE"),
    ETC(4, "ETC");

    private int code;
    private String placeholder;

    DistanceUnit(int code, String placeholder) {
        this.code = code;
        this.placeholder = placeholder;
    }

    public static DistanceUnit findByCode(int code) {
        return Arrays.stream(DistanceUnit.values())
                .filter(unit -> unit.code == code)
                .findAny()
                .orElse(ETC);
    }

    public int getCode() {
        return code;
    }

    public String getPlaceholder() {
        return placeholder;
    }
}
