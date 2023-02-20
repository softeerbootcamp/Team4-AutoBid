package com.codesquad.autobid.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Table("users") @Getter @Setter
@ToString
public class User implements Serializable {
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

    public void update(UserVO userVO) {
        this.uid = userVO.getId();
        this.birthdate = parseDate(userVO.getBirthdate());
        this.email = userVO.getEmail();
        this.mobilenum = userVO.getMobileNum();
        this.name = userVO.getName();
    }

    public static User of(UserVO userVo, String refreshToken){
        return new User(userVo.getId(), userVo.getEmail(), userVo.getName(), userVo.getMobileNum(), parseDate(userVo.getBirthdate()), LocalDateTime.now(), LocalDateTime.now(), refreshToken);
    }

    public static LocalDate parseDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
//        return LocalDate.parse(date, formatter);
        return LocalDate.now();
    }

}
