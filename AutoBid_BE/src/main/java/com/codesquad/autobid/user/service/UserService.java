package com.codesquad.autobid.user.service;

import com.codesquad.autobid.user.domain.Users;
import com.codesquad.autobid.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    /**
     * TODO
     *  1. Stream filter 여러개 적용을 어떻게 하는가 ( login method )
     * **/

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public void login(Map<String,String> userData) {

        Set<String> collect = userData.entrySet().stream()
                .filter(e -> e.getKey().startsWith("id"))
                .filter(e -> e.getKey().startsWith("email"))
                .filter(e -> e.getKey().startsWith("name"))
                .filter(e -> e.getKey().startsWith("mobileNum"))
                .filter(e -> e.getKey().startsWith("birthdate"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        for(String s : collect){
            logger.info("s is : {}",s);
        }

//        logger.info("id : {}",data);
    }
}
