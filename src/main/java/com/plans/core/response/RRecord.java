package com.plans.core.response;

import java.time.LocalDateTime;

import com.plans.core.model.DeviceRecord;

import lombok.Data;

@Data
public class RRecord {
    private LocalDateTime timestamp;
    private Double temperature;
    private Double humidity;
    private Double pressure;

    public RRecord(DeviceRecord deviceRecord) {
        this.timestamp = deviceRecord.getTimestamp();
        this.temperature = deviceRecord.getTemperature();
        this.humidity = deviceRecord.getHumidity();
        this.pressure = deviceRecord.getPressure();
    }
}
