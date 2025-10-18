package com.desarrollox.learncompany.domain.model;

public class Login {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private User user;
    private String scope;

    public Login() {
    }

    public Login(String accessToken, String refreshToken, String tokenType, Long expiresIn, User user, String scope) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.user = user;
        this.scope = scope;
    }

    public static Login succes(String accessToken, String refreshToken, Long expiresIn, User user) {
        return new Login(accessToken, refreshToken, "Bearer", expiresIn, user, "read write");
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

}