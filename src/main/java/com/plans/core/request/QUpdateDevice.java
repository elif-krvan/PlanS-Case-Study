package com.plans.core.request;

import java.time.ZoneId;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QUpdateDevice {
    @NotNull
    private UUID id;
    
    private String name;
    private Double lat;
    private Double lon;
    private ZoneId zone;
    private String username;
}