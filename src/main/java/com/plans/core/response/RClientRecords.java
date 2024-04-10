package com.plans.core.response;

import java.util.List;
import java.util.stream.Collectors;

import com.plans.core.model.DeviceRecord;
import com.plans.core.model.IoTDevice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RClientRecords {
    private RDeviceData device;
    private List<RRecord> records;

    public RClientRecords(IoTDevice device, List<DeviceRecord> records) {
        this.device = new RDeviceData(device);
        this.records = records.stream().map(record -> new RRecord(record)).collect(Collectors.toList());;
    }
}
