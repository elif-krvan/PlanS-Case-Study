package com.plans.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QUserLogin {
    @NotNull
    @Email(message = "Email field must be in a valid email format")
    private String email;

    @NotBlank
    private String password;

    // public QUserLogin(
    //             @JsonProperty("email") String email,
    //             @JsonProperty("password") String password) {

    //     this.email = email;
    //     this.password = password;
    // }
}