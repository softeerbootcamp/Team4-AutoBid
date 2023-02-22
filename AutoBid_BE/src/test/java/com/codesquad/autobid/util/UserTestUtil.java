package com.codesquad.autobid.util;

import com.codesquad.autobid.user.domain.User;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTestUtil {
    public static User getNewUser() {
        return new User("uid#1", "email#1", "name#1", "phoneNumber#1", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now(), "refreshToken#1");
    }

    public static User saveUser(User user) {
        try {
            Field id = user.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(user, 1l);
            return user;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
