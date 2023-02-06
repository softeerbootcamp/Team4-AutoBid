package com.codesquad.autobid.user.service;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.handler.user.UserHandler;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.domain.UserVO;
import com.codesquad.autobid.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * TODO
     *  1. Stream filter 여러개 적용을 어떻게 하는가 ( login method )
     **/

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserHandler userHandler;

    @Autowired
    public UserService(UserRepository userRepository, UserHandler userHandler) {
        this.userRepository = userRepository;
        this.userHandler = userHandler;
    }

    public User findUser(OauthToken oauthToken) {
        UserVO userVO = userHandler.userProfileAPICall(oauthToken);
        logger.info("a: {}",userVO.getEmail());
        User user = userRepository.findByUid(userVO.getId()).orElse(User.of(userVO, oauthToken.getRefresh_token()));
        return userRepository.save(user);
    }
}
