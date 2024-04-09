package com.plans.core.response;

import com.plans.core.model.User;

import jakarta.servlet.http.Cookie;
import lombok.Data;

@Data
public class RLoginUser {
    private RRegisterUser user;
    private Cookie accessToken;
    private Cookie refreshToken;

    public RLoginUser(User user, Cookie accessToken, Cookie refreshToken) {
        this.user = new RRegisterUser(user);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
