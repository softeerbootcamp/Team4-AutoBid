package com.codesquad.autobid.user.repository;

import com.codesquad.autobid.user.domain.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class UserRepositoryTest {

    Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setting() {

    }

    @Test
    void save() {
        LocalDateTime formatted = LocalDateTime.now();

//        Users user = new Users("123456","email@email.com","성준", "01012341234","19960214", formatted, formatted,"accessToken","refreshToken");

//        Users saveUser = userRepository.save(user);
//
//        Assertions.assertThat(saveUser).isEqualTo(user);
    }

    @Test
    void findById() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
