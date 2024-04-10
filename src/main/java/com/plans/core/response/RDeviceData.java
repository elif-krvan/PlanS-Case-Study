package com.plans.core.response;

import java.util.UUID;

import com.plans.core.model.IoTDevice;

import lombok.Data;

@Data
public class RDeviceData {
    private UUID id;
    private Double lat;
    private Double lon;
    private String name;

    public RDeviceData(IoTDevice device) {
        this.id = device.getId();
        this.lat = device.getLat();
        this.lon = device.getLon();
        this.name = device.getName();
    }
}
