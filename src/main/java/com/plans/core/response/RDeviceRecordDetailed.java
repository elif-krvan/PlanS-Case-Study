package com.plans.core.response;

import java.util.List;

import com.plans.core.model.IoTDevice;
import com.plans.core.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RDeviceRecordDetailed {
    private RDeviceData device;
    private RUser client;
    private List<RRecord> records;

    public RDeviceRecordDetailed(IoTDevice device, List<RRecord> records) {
        this.device = new RDeviceData(device);
        this.records = records;
        
        User user = device.getEndUser().getUser();
        this.client = new RUser(
            user.getUsername(), 
            user.getEmail());
    }
}
