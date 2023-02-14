package com.codesquad.autobid.websocket.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterDto {
    String session;
    String roomId;
}
