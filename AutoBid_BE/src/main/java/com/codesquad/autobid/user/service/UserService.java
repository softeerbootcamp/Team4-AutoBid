package com.codesquad.autobid.user.service;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.handler.AuthHandler;
import com.codesquad.autobid.handler.user.UserHandler;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.domain.UserVO;
import com.codesquad.autobid.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthHandler authHandler;
    private final UserHandler userHandler;

    @Autowired
    public UserService(UserRepository userRepository, AuthHandler authHandler, UserHandler userHandler) {
        this.userRepository = userRepository;
        this.authHandler = authHandler;
        this.userHandler = userHandler;
    }

    @Transactional
    public User findUser(OauthToken oauthToken) {
        UserVO userVO = userHandler.userProfileAPICall(oauthToken);
        User user = userRepository.findByUid(userVO.getId()).orElseGet(() -> User.of(userVO, oauthToken.getRefreshToken()));
        return userRepository.save(user);
    }

    public Optional<User> findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            log.info("userID : {}",user.get().getId());
            return user;
        }
        return Optional.empty();
    }
}
