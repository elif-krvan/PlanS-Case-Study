package com.plans.core.response;

import java.util.UUID;

import com.plans.core.model.IoTDevice;
import com.plans.core.model.Location;

import lombok.Data;

@Data
public class RDeviceData {
    private UUID id;
    private String name;
    private Location location;

    public RDeviceData(IoTDevice device) {
        this.id = device.getId();
        this.location = device.getLocation();
        this.name = device.getName();
    }
}
