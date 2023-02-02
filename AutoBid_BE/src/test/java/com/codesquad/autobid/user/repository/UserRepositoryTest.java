package com.codesquad.autobid.user.repository;

import com.codesquad.autobid.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

//    @Value("${spring.datasource.driver-class-name}")
//    private String driver;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String pw;

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setting() {
//        try {
//            Class.forName(driver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    void save() {
        User user = new User("tjdwns4537", "sungjun", "01074724537");
//        try(Connection con =
//                    DriverManager.getConnection(
//                            url,
//                            username,
//                            pw)) {
//            logger.info("con : {}", con);
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
        userRepository.save(user);
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
