package com.plans.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.plans.core.exception.NotFoundException;
import com.plans.core.exception.SomethingWentWrongException;
import com.plans.core.model.DeviceRecord;
import com.plans.core.model.EndUser;
import com.plans.core.model.IoTDevice;
import com.plans.core.repository.IDeviceRepository;
import com.plans.core.repository.IEndUserRepository;
import com.plans.core.repository.IRecordRepository;
import com.plans.core.request.QAddRecord;
import com.plans.core.request.QFindRecordByClient;
import com.plans.core.request.QFindRecordByDevice;
import com.plans.core.response.RClientRecords;
import com.plans.core.response.RDeviceRecordDetailed;
import com.plans.core.response.RDeviceRecordDetailedByClient;
import com.plans.core.response.RRecord;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordService {

    private final IDeviceRepository deviceRepository;
    private final IRecordRepository recordRepository;
    private final IEndUserRepository endUserRepository;

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
    
    public RDeviceRecordDetailed getRecordsByDevice(QFindRecordByDevice search) throws Exception {
        try {
            if (!search.isValid()) {
                // Handle validation error
                throw new ValidationException("End time must be after start time");
            }
            
            // If device with the given UUID does not exist, throw an error
            IoTDevice device = deviceRepository.findById(search.getDeviceId())
                                         .orElseThrow(() -> new NotFoundException("Device is not found!"));

            List<DeviceRecord> records = recordRepository.findByDeviceIdAndTimestampBetween(
                search.getDeviceId(), search.getStartTime(), search.getEndTime());

            log.info("Records of device {} is retrieved", search.getDeviceId());

            List<RRecord> recordList = records.stream().map(record -> new RRecord(record)).collect(Collectors.toList()); 
            return new RDeviceRecordDetailed(device, recordList);
        } catch (ValidationException e) {
            log.error("Device records cannot be retrieved since due to validation error", e);
            throw e;
        } catch (NotFoundException e) {
            log.error("Device records cannot be retrieved since device {} does not exist", search.getDeviceId());
            throw e;
        } catch (Exception e) {
            log.error("Device records cannot be retrieved for device {}", search.getDeviceId(), e);
            throw new SomethingWentWrongException();
        }
    }

    public RDeviceRecordDetailedByClient getRecordsByClient(QFindRecordByClient search) throws Exception {
        try {
            if (!search.isValid()) {
                // Handle validation error
                throw new ValidationException("End time must be after start time");
            }
            
            // If device with the given UUID does not exist, throw an error
            EndUser endUser = endUserRepository.findByUserUsername(search.getUsername())
                                         .orElseThrow(() -> new NotFoundException("User is not found!"));

            List<DeviceRecord> records = recordRepository.findByDeviceEndUserUserUsernameAndTimestampBetween(
                search.getUsername(), search.getStartTime(), search.getEndTime());

            Map<IoTDevice, List<DeviceRecord>> recordsByDevice = records.stream()
                    .collect(Collectors.groupingBy(DeviceRecord::getDevice));

            Set<RClientRecords> clientRecords = recordsByDevice.entrySet().stream()
                    .map(entry -> {
                        return new RClientRecords(entry.getKey(), entry.getValue());
                    }).collect(Collectors.toSet());

            log.info("Records of client {} is retrieved", search.getUsername());
 
            return new RDeviceRecordDetailedByClient(endUser, clientRecords);
        } catch (ValidationException e) {
            log.error("Device records cannot be retrieved since due to validation error", e);
            throw e;
        } catch (NotFoundException e) {
            log.error("Device records cannot be retrieved since client {} does not exist", search.getUsername());
            throw e;
        } catch (Exception e) {
            log.error("Device records cannot be retrieved for client {}", search.getUsername(), e);
            throw new SomethingWentWrongException();
        }
    }
}
