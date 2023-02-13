package com.codesquad.autobid.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserImpoResponse {
    private Long id;
    private String userName;
    private String email;
    private String phone;

    public static UserImpoResponse create(Long id, String userName, String email, String phone) {
        UserImpoResponse user = new UserImpoResponse();
        user.setId(id);
        user.setUserName(userName);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

}
