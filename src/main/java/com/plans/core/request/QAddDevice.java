package com.plans.core.request;

import java.time.ZoneId;

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

    @NotNull
    private ZoneId zone;

    @NotBlank
    private String username;
}