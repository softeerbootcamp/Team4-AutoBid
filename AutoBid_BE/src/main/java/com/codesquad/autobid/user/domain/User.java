package com.codesquad.autobid.user.domain;

public class User {
    private Long id;
    private String userid;
    private String username;
    private String phonenumber;

    public User() {

    }

    public User(String userid, String username, String phonenumber) {
        this.userid = userid;
        this.username = username;
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
