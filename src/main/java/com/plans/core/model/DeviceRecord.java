package com.plans.core.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.plans.core.model.composite_id.RecordId;
import com.plans.core.request.QAddDevice;
import com.plans.core.request.QAddRecord;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
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
@Table(name = "records")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RecordId.class)
public class DeviceRecord {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device", referencedColumnName = "id", insertable = false, updatable = false)
    private IoTDevice device;

    @Id
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "temperature", nullable = false)
    @Min(value = -100, message = "Temperature must be at least -100") // TODO should we do this, this data comes from the sensors anyway
    @Max(value = 100, message = "Temperature must not exceed 100")
    private Double temperature;

    @Column(name = "humidity", nullable = false)
    @Min(value = 0, message = "Humidity must be at least 0")
    @Max(value = 100, message = "Humidity must not exceed 100")
    private Double humidity;

    @Column(name = "pressure", nullable = false)
    @Min(value = 800, message = "Pressure must be at least 700")
    @Max(value = 1200, message = "Pressure must not exceed 1300")
    private Double pressure;

    public DeviceRecord(QAddRecord record, IoTDevice device) {
        this.device = device;
        this.timestamp = record.getTimestamp();
        this.temperature = record.getTemperature();
        this.humidity = record.getHumidity();
        this.pressure = record.getPressure();
    }
}
