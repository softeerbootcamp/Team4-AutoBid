package com.codesquad.autobid.user.domain;

import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserVO {

    @Column("user_uid")
    private String uid;

    @Column("user_email")
    private String email;

    @Column("user_name")
    private String name;

    @Column("user_mobilenum")
    private String mobilenum;

    @Column("user_birthdate")
    private LocalDate birthdate;

    private String lang;

    private String social;

    public UserVO() {

    }

    public UserVO(String uid, String email, String name, String mobilenum, LocalDate birthdate, String lang, String social) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.mobilenum = mobilenum;
        this.birthdate = birthdate;
        this.lang = lang;
        this.social = social;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }
}
