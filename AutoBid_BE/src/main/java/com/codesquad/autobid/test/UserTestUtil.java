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
	private final UserRepository userRepository;

	public static User TEST_USER_1 = new User(1L, "useruild", "email", "name", "mobilenum", LocalDate.now(),
		LocalDateTime.now(), LocalDateTime.now(), "refresh");

}
