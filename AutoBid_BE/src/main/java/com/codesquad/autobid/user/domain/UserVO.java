package com.codesquad.autobid.user.domain;

import org.springframework.data.relational.core.mapping.Column;

public class UserVO {

    @Column("user_uid")
    private String userUid;

    @Column("user_email")
    private String userEmail;

    @Column("user_name")
    private String userName;

    @Column("user_mobilenum")
    private String userMobilenum;

    @Column("user_birthdate")
    private String userBirthdate;

    private String lang;

    private String social;

    public UserVO() {

    }

    public UserVO(String userUid, String userEmail, String userName, String userMobilenum, String userBirthdate, String lang, String social) {
        this.userUid = userUid;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userMobilenum = userMobilenum;
        this.userBirthdate = userBirthdate;
        this.lang = lang;
        this.social = social;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobilenum() {
        return userMobilenum;
    }

    public void setUserMobilenum(String userMobilenum) {
        this.userMobilenum = userMobilenum;
    }

    public String getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
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
