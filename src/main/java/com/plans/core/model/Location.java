package com.plans.core.model;

import java.time.ZoneId;
import com.plans.core.model.composite_id.LocationId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LocationId.class)
public class Location {

    @Id
    @Column(name = "lat", nullable = false, unique = false)
    @Min(value = -90, message = "Latitude must be between -90 and +90")
    @Max(value = 90, message = "Latitude must be between -90 and +90")
    private Double lat;
    
    @Id
    @Column(name = "long", nullable = false, unique = false)
    @Min(value = -180, message = "Longitude  must be between -180 and +180")
    @Max(value = 180, message = "Longitude  must be between -180 and +180")
    private Double lon;

    @NotNull
    @Column(name = "timezone", nullable = false)
    private ZoneId timezone;
}
