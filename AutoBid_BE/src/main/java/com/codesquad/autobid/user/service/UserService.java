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
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserHandler userHandler;

    @Autowired
    public UserService(UserRepository userRepository, UserHandler userHandler) {
        this.userRepository = userRepository;
        this.userHandler = userHandler;
    }

    @Transactional
    public User findUser(OauthToken oauthToken) {
        UserVO userVO = userHandler.userProfileAPICall(oauthToken);
        User user = userRepository.findByUid(userVO.getId()).orElseGet(() -> User.of(userVO, oauthToken.getRefreshToken()));
        return userRepository.save(user);
    }
}
