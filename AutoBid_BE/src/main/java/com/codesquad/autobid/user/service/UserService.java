package com.codesquad.autobid.user.service;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.handler.user.UserHandler;
import com.codesquad.autobid.user.domain.UserVO;
import com.codesquad.autobid.user.domain.Users;
import com.codesquad.autobid.user.repository.UserRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    /**
     * TODO
     *  1. Stream filter 여러개 적용을 어떻게 하는가 ( login method )
     * **/

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepositoryInterface userRepository;

    private final UserHandler userHandler;

    @Autowired
    public UserService(UserRepositoryInterface userRepository, UserHandler userHandler) {
        this.userRepository = userRepository;
        this.userHandler = userHandler;
    }

    public void findUser(OauthToken oauthToken) {
        UserVO userVO = userHandler.userProfileAPICall(oauthToken);
        Optional<Users> user = userRepository.findByUid(userVO.getUid());
        // user.ifPresentOrElse((user)-> { // user 있으면 1번 람다, 없으면 2번 람다
        //     user.update(userVO);
        //     userRepository.save(user);
        // }, ()-> userRepository.save(Users.from(userVo)));
        if (user.isPresent()) {
            user.update(userVO);
        }
        userRepository.save(user);
        /**
         *  TODO
         *   1. api 조회
         *   2. 데이터베이스에 유저 있는지 (uid) 확인
         *   3. uid 존재하면 꺼내줌
         *   4. 없으면 INSERT
         *
         * **/

    }

    public static Users mapperUser(UserVO userVO) {
        Users user = new Users();
        user.setUid(userVO.getUid());
        user.setEmail(userVO.getEmail());
        user.setBirthdate(userVO.getBirthdate());
        user.setMobilenum(userVO.getMobilenum());
        user.setName(userVO.getName());
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        return user;
    }


}
