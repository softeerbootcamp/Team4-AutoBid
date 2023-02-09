package com.codesquad.autobid.handler.car.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DistanceVO {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("value")
    private Long distance;
    @JsonProperty("unit")
    private int unit;
    @JsonProperty("msgId")
    private String messageId;
}
