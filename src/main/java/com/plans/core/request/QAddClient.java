package com.plans.core.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QAddClient {
    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "Email field must be in a valid email format")
    private String email;
}