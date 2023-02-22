package com.codesquad.autobid.user.repository;

import com.codesquad.autobid.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUid(String uid);


}
