package com.plans.core.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QAddDevice {
    @NotBlank
    private String name;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    @NotBlank
    private String username;
}