package com.newProject.first.DTO;

public class RefreshResponse {
    String accessToken;

    public RefreshResponse(String accessToken) {
        this.accessToken=accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
