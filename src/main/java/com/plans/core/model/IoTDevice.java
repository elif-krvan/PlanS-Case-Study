package com.plans.core.model;

import java.util.UUID;

import com.plans.core.request.QAddDevice;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "devices")
@AllArgsConstructor
@NoArgsConstructor
public class IoTDevice {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank // TODO add size constraint
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumns({ // test this
        @JoinColumn(name = "lat", referencedColumnName = "lat"),
        @JoinColumn(name = "long", referencedColumnName = "long")
    })
    private Location location;
   
    @ManyToOne(fetch = FetchType.LAZY) // TODO , cascade = CascadeType.PERSIST
    @JoinColumn(name = "end_user", referencedColumnName = "user_id", nullable = false)
    private EndUser endUser;

    public IoTDevice(UUID id, QAddDevice device, EndUser endUser) {
        this.id = id;
        this.name = device.getName();
        this.location = new Location(device.getLat(), device.getLon(), device.getZone());
        this.endUser = endUser;
    }
}
