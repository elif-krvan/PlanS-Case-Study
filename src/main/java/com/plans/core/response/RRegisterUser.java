package com.plans.core.response;

import com.plans.core.model.User;

import lombok.Data;

@Data
public class RRegisterUser {
    private String username;
    private String email;

    public RRegisterUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
