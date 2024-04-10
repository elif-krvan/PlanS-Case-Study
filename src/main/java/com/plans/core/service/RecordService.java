package com.plans.core.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.plans.core.enums.Role;
import com.plans.core.exception.AlreadyFoundException;
import com.plans.core.exception.NotFoundException;
import com.plans.core.exception.SomethingWentWrongException;
import com.plans.core.model.DeviceRecord;
import com.plans.core.model.EndUser;
import com.plans.core.model.IoTDevice;
import com.plans.core.model.Location;
import com.plans.core.model.User;
import com.plans.core.repository.IAccountRepository;
import com.plans.core.repository.IDeviceRepository;
import com.plans.core.repository.IEndUserRepository;
import com.plans.core.repository.ILocationRepository;
import com.plans.core.repository.IRecordRepository;
import com.plans.core.request.QAddClient;
import com.plans.core.request.QAddDevice;
import com.plans.core.request.QAddRecord;
import com.plans.core.request.QUpdateDevice;
import com.plans.core.request.QUpdateUser;
import com.plans.core.request.QUser;
import com.plans.core.response.RDevice;
import com.plans.core.response.RRegisterClient;
import com.plans.core.response.RUser;
import com.plans.core.utils.PasswordGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordService {

    private final IEndUserRepository endUserRepository; // TODO is this necessary
    private final IDeviceRepository deviceRepository;
    private final ILocationRepository locationRepository;
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

            return newRecord; //TODO response class          
        } catch (NotFoundException e) {
            log.error("Device record cannot be created since device {} does not exist", record.getDeviceId());
            throw e;
        } catch (Exception e) {
            log.error("Device record cannot be created for device {}", record.getDeviceId(), e);
            throw new SomethingWentWrongException();
        }
    }
}
