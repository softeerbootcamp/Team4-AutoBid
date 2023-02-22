package com.codesquad.autobid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OauthToken {

    public static final String ACCESS_TOKEN_KEY = "accessToken";
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;
}
