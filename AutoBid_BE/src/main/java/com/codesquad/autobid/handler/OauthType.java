package com.codesquad.autobid.handler;

public enum OauthType {

	NEW("authorization_code", "code"),
	REFRESH("refresh_token", "refresh_token"),
	DELETE("delete", "access_token");

	private String grantType;
	private String tokenType;

	OauthType(String grantType, String tokenType) {
		this.grantType = grantType;
		this.tokenType = tokenType;
	}

	public String getGrantType() {
		return grantType;
	}

	public String getTokenType() {
		return tokenType;
	}
}
