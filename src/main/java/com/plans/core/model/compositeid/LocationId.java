package com.plans.core.model.compositeid;

import java.io.Serializable;
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