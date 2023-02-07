package com.codesquad.autobid.user.controller;

public enum Session {

    NAME( "NAME");

    private String authCode;

    Session( String authCode ) {
        this.authCode = authCode;
    }

    public String getCode() {
        return this.authCode;
    }
}
