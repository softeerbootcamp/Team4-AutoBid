package com.codesquad.autobid.user.service;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.codesquad.autobid.user.domain.Users;
import com.codesquad.autobid.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void login(UserVO userVO, OauthToken oauthToken) {
        Users user = mapperUser(userVO);
        user.setAccessToken(oauthToken.getAccess_token());
        user.setRefreshToken(oauthToken.getRefresh_token());
        userRepository.save(user);
        logger.debug("userUID : {}",userVO.getUserUid());
    }

    public Users mapperUser(UserVO userVO) {
        Users user = new Users();
        user.setUserUid(userVO.getUserUid());
        user.setUserEmail(userVO.getUserEmail());
        user.setUserBirthdate(userVO.getUserBirthdate());
        user.setUserMobilenum(userVO.getUserMobilenum());
        user.setUserName(userVO.getUserName());
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        return user;
    }
}
