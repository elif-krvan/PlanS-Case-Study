package com.plans.core.response;

import com.plans.core.model.User;

import lombok.Data;

@Data
public class RUser {
    private String username;
    private String email;

    public RUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
