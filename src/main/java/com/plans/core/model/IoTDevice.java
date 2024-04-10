package com.plans.core.model;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.plans.core.request.QAddDevice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToOne
    @JoinColumns({ // test this
        @JoinColumn(name = "lat", referencedColumnName = "lat"),
        @JoinColumn(name = "long", referencedColumnName = "long")
    })
    private Location location;
   
    @OnDelete(action = OnDeleteAction.CASCADE)
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
