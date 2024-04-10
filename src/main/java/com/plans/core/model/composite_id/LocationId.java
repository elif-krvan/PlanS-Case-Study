package com.plans.core.model.composite_id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Embeddable;

import com.plans.core.model.IoTDevice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationId implements Serializable {
    private Double lat;
    private Double lon;
}