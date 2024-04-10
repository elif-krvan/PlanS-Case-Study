package com.plans.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RRegisterClient {
    private String username;
    private String email;
    private String password;
}
