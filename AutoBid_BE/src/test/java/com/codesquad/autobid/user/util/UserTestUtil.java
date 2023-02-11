package com.codesquad.autobid.user.util;

import com.codesquad.autobid.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTestUtil {
    public static User getNewUser() {
        return new User("uid#1", "email#1", "name#1", "phoneNumber#1", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now(), "refreshToken#1");
    }
}
