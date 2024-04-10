package com.plans.core.service;

import org.springframework.stereotype.Service;

import com.plans.core.exception.NotFoundException;
import com.plans.core.exception.SomethingWentWrongException;
import com.plans.core.model.DeviceRecord;
import com.plans.core.model.IoTDevice;
import com.plans.core.repository.IDeviceRepository;
import com.plans.core.repository.IRecordRepository;
import com.plans.core.request.QAddRecord;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordService {

    private final IDeviceRepository deviceRepository;
    private final IRecordRepository recordRepository;

    @Transactional
    public DeviceRecord createRecord(QAddRecord record) throws Exception {
        try {
            // If device with the given UUID does not exist, throw an error
            IoTDevice device = deviceRepository.findById(record.getDeviceId())
                                         .orElseThrow(() -> new NotFoundException("Device is not found!"));

            DeviceRecord newRecord = recordRepository.save(new DeviceRecord(
                record, device
            ));

            log.info("A new record for device {} is created.", newRecord.getDevice().getId());

            return newRecord;        
        } catch (NotFoundException e) {
            log.error("Device record cannot be created since device {} does not exist", record.getDeviceId());
            throw e;
        } catch (Exception e) {
            log.error("Device record cannot be created for device {}", record.getDeviceId(), e);
            throw new SomethingWentWrongException();
        }
    }
}
