package com.codesquad.autobid.user.repository;

import com.codesquad.autobid.user.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepositoryInterface extends CrudRepository<Users, Long> {

    Optional<Users> findByUid(String uid);






}
