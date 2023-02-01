package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.user.User;

import java.time.LocalDateTime;

public class Car {

    private Long id;
    private User user;
    // todo: state의 역할?
    private String state;
    private Long distance;
    private String name;
    private String sellName;
    // todo: carUid 역할?
    private Long carUid;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
