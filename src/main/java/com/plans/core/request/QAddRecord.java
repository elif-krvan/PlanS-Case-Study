package com.plans.core.request;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QAddRecord {
    private UUID deviceId;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private Double temperature;

    @NotNull
    private Double humidity;

    @NotNull
    private Double pressure;
}