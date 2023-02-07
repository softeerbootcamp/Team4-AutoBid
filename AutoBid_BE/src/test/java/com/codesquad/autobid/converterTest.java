package com.codesquad.autobid;

import com.codesquad.autobid.car.domain.Distance;
import com.codesquad.autobid.user.service.EnumToStringConverter;
import org.junit.jupiter.api.Test;

public class converterTest {

    public enum enumT {
        A("대기","1"),
        B("시작","2"),
        C("정지","3");

        private String de;
        private String code;

        enumT (String dea, String cc) {
            this.de = dea;
            this.code = cc;
        }
    }

    @Test
    public void converter() {

        EnumToStringConverter enumToStringConverter = new EnumToStringConverter();
//        enumToStringConverter.convert(Distance.from())

    }
}
