package com.plans.core.model;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "devices")
@NoArgsConstructor
public class IoTDevice {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "lat", nullable = false, unique = true)
    @Min(value = -90, message = "Latitude must be between -90 and +90")
    @Max(value = 90, message = "Latitude must be between -90 and +90")
    private Double lat;
    
    @NotNull
    @Column(name = "long", nullable = false, unique = true)
    @Min(value = -180, message = "Longitude  must be between -180 and +180")
    @Max(value = 180, message = "Longitude  must be between -180 and +180")
    private Double lon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "end_user", referencedColumnName = "user_id", nullable = false)
    private EndUser endUser;
}
