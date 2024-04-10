package com.plans.core.model.composite_id;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.plans.core.model.IoTDevice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordId implements Serializable {
    private IoTDevice device;
    private LocalDateTime timestamp;
}