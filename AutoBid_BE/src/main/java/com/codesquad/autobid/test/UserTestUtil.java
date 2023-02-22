package com.codesquad.autobid.test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserTestUtil {

	public static User TEST_USER_1 = new User(1L, "useruild", "email", "name", "mobilenum", LocalDate.now(),
		LocalDateTime.now(), LocalDateTime.now(), "refresh");

	public static User RANDOM_USER = new User(10L, "f63bb014-8357-4ed4-bce8-c62aea983f8a", "random_lee@naver.com",
		"이수균", "+821094274013", LocalDate.of(2023,2,6), LocalDateTime.now(), LocalDateTime.now(), "FOYJFTGDXNYJKMHDIT3ZWW");

}
