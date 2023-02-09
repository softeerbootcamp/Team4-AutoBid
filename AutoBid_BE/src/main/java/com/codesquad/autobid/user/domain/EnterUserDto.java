package com.codesquad.autobid.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterUserDto {
    private String roomNum;
    private String userName;
    private String mobileNum;

    public EnterUserDto() {
    }

    public EnterUserDto(String roomNum, String userName, String mobileNum) {
        this.roomNum = roomNum;
        this.userName = userName;
        this.mobileNum = mobileNum;
    }
}
