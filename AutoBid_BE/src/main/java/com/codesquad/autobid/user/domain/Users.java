package com.codesquad.autobid.user.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("users")
public class Users {

    @Id
    @Column("user_id")
    private Long id;

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

    @Column("create_at")
    private LocalDateTime createAt;

    @Column("update_at")
    private LocalDateTime updateAt;

    @Column("access_token")
    private String accessToken;

    @Column("refresh_token")
    private String refreshToken;

    public Users() {

    }

    public Users(String userUid, String userEmail, String userName, String userMobilenum, String userBirthdate, LocalDateTime createAt, LocalDateTime updateAt, String accessToken, String refreshToken) {
        this.userUid = userUid;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userMobilenum = userMobilenum;
        this.userBirthdate = userBirthdate;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
