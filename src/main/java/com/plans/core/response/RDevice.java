package com.plans.core.response;

import com.plans.core.model.IoTDevice;
import com.plans.core.model.User;

import lombok.Data;

@Data
public class RDevice {
    private RDeviceData device;
    private RUser client;

    public RDevice(IoTDevice device) {
        this.device = new RDeviceData(device);
        this.client = new RUser(
            device.getEndUser().getUser().getUsername(), 
            device.getEndUser().getUser().getEmail());
    }
}
