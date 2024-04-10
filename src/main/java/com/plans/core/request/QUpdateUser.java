package com.plans.core.request;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QUpdateUser {
    @NotNull
    private UUID userId;

    private String username;

    @Email(message = "Email field must be in a valid email format")
    private String email;

    private String password;
}
