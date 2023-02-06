package com.codesquad.autobid.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Table("users")
public class User {

    @Id
    @Column("user_id")
    private Long id;

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

    @Column("created_at")
    private LocalDateTime createAt;

    @Column("updated_at")
    private LocalDateTime updateAt;

    @Column("refresh_token")
    private String refreshToken;

    public User() {

    }

    public User(String userUid, String userEmail, String userName, String userMobilenum, LocalDate userBirthdate, LocalDateTime createAt, LocalDateTime updateAt, String refreshToken) {
        this.uid = userUid;
        this.email = userEmail;
        this.name = userName;
        this.mobilenum = userMobilenum;
        this.birthdate = userBirthdate;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.refreshToken = refreshToken;
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

    public void update(UserVO userVO) {
        this.uid = userVO.getId();
        this.birthdate = parseDate(userVO.getBirthdate());
        this.email = userVO.getEmail();
        this.mobilenum = userVO.getMobilenum();
        this.name = userVO.getName();
    }

    public static User of(UserVO userVo, String refreshToken){
        return new User(userVo.getId(), userVo.getEmail(), userVo.getName(), userVo.getMobilenum(), parseDate(userVo.getBirthdate()), LocalDateTime.now(), LocalDateTime.now(), refreshToken);
    }

    public static LocalDate parseDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
//        return LocalDate.parse(date, formatter);
        return LocalDate.now();
    }

}
