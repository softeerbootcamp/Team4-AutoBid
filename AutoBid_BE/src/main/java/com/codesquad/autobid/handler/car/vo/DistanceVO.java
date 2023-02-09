package com.codesquad.autobid.handler.car.vo;

import com.codesquad.autobid.handler.car.enums.DistanceUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DistanceVO {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("value")
    private Long distance;
    @JsonProperty("unit")
    private int unitCode;
    @JsonProperty("msgId")
    private String messageId;
}
