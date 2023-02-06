package com.codesquad.autobid.user.domain;

public class UserVO {

    private String id;

    private String email;

    private String name;

    private String mobileNum;

    private String birthdate;

    private String lang;

    private String social;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return mobileNum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobileNum = mobilenum;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
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


    @Override
    public String toString() {
        return "UserVO{" +
                "uid='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", mobilenum='" + mobileNum + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", lang='" + lang + '\'' +
                ", social='" + social + '\'' +
                '}';
    }
}
