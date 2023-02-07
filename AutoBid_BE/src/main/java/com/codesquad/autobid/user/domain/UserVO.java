package com.codesquad.autobid.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserVO {

    private String id;

    private String masterId;

    private String email;

    private String name;

    private String mobileNum;

    private String birthdate;

    private String status;

    private String lang;

    private String country;

    private String dialect;

    private String social;

    private String password;

    private String passwordDate;

    private String pinDate;

    private String signUpDate;

     public UserVO() {

    }

    public UserVO(String id, String email, String name, String mobilenum, String birthdate, String lang, String social) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.mobileNum = mobilenum;
        this.birthdate = birthdate;
        this.lang = lang;
        this.social = social;
    }

    public UserVO(String masterId, String email, String name, String mobileNum, String birthdate, String status, String lang, String country, String dialect, String social, String password, String passwordDate, String pinDate, String signUpDate) {
        this.masterId = masterId;
        this.email = email;
        this.name = name;
        this.mobileNum = mobileNum;
        this.birthdate = birthdate;
        this.status = status;
        this.lang = lang;
        this.country = country;
        this.dialect = dialect;
        this.social = social;
        this.password = password;
        this.passwordDate = passwordDate;
        this.pinDate = pinDate;
        this.signUpDate = signUpDate;
    }
}
