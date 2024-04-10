package com.plans.core.request;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QUpdateDevice {
    @NotNull
    private UUID id;
    
    private String name;

    @Min(value = -90, message = "Latitude must be between -90 and +90")
    @Max(value = 90, message = "Latitude must be between -90 and +90")
    private Double lat;

    @Min(value = -180, message = "Longitude  must be between -180 and +180")
    @Max(value = 180, message = "Longitude  must be between -180 and +180")
    private Double lon;
    
    private String username;
}