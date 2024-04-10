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
import com.plans.core.model.EndUser;
import com.plans.core.model.IoTDevice;
import com.plans.core.model.User;
import com.plans.core.repository.IAccountRepository;
import com.plans.core.repository.IDeviceRepository;
import com.plans.core.repository.IEndUserRepository;
import com.plans.core.request.QAddClient;
import com.plans.core.request.QAddDevice;
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
public class IoTDeviceService {

    private final IEndUserRepository endUserRepository; // TODO is this necessary
    private final IDeviceRepository deviceRepository;

    @Transactional
    public RDevice createDevice(QAddDevice device) throws Exception {
        try {
            // If user with the given username does not exist, throw an error
            EndUser user = endUserRepository.findByUserUsername(device.getUsername())
                                         .orElseThrow(() -> new NotFoundException("User is not found!"));

            IoTDevice newDevice = deviceRepository.save(new IoTDevice(
                UUID.randomUUID(),
                device,
                user
            ));

            log.info("A new IoT device with id {} is registered for user {}.", newDevice.getId(), device.getUsername());

            return new RDevice(newDevice); //TODO response class          
        } catch (NotFoundException e) {
            log.error("IoT device cannot be created since client with username does not exist {}", device.getUsername());
            throw e;
        } catch (Exception e) {
            log.error("IoT device cannot be created for client {}", device.getUsername(), e);
            throw new SomethingWentWrongException();
        }
    }

    public List<RDevice> getAllDevices() throws Exception {
        try {
            return deviceRepository.findAll().stream().map(device -> new RDevice(device)).collect(Collectors.toList()); 
        } catch (Exception e) {
            log.error("IoT devices cannot be retrieved", e);
            throw new SomethingWentWrongException();
        }
    }
    
    public RDevice getDeviceById(UUID deviceId) throws Exception {
        try {
            IoTDevice device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device is not found!")); 
            
            return new RDevice(device);
        } catch (NotFoundException e) {
            log.error("IoT device {} cannot be retrieved since it does not exist", deviceId);
            throw e;
        } catch (Exception e) {
            log.error("IoT device {} cannot be retrieved since", deviceId, e);
            throw new SomethingWentWrongException();
        }
    }

    public List<RDevice> getDeviceByClient(String username) throws Exception {
        try {
            endUserRepository.findByUserUsername(username).orElseThrow(() -> new NotFoundException("User is not found!"));  

            return deviceRepository.findByEndUserUserUsername(username).stream().map(device -> new RDevice(device)).collect(Collectors.toList()); 
        } catch (NotFoundException e) {
            log.error("IoT devices of user {} cannot be retrieved since user does not exist", username);
            throw e;
        } catch (Exception e) {
            log.error("IoT devices of user {} cannot be retrieved", username, e);
            throw new SomethingWentWrongException();
        }
    }
    
    @Transactional
    public void removeDevice(UUID id) throws Exception {
        try {
            deviceRepository.deleteById(id);

            log.info("Device {} is removed", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Device {} couldn't be removed since it does not exist", id, e);
            throw new NotFoundException("Device is not found!");
        } catch (Exception e) {
            log.error("Device {} couldn't be removed", id, e);
            throw new SomethingWentWrongException();
        }
    }    

    @Transactional
    public RDevice updateDevice(QUpdateDevice updateDevice) throws Exception {
        try {
            // If device with id does not exist, throw an exception
            IoTDevice device = deviceRepository.findById(updateDevice.getId())
                                         .orElseThrow(() -> new NotFoundException("Device is not found!"));
            
            // Set new fields to the user
            if (updateDevice.getName() != null) {
                device.setName(updateDevice.getName());
            }

            if (updateDevice.getLat() != null) {
                device.setLat(updateDevice.getLat());
            }

            if (updateDevice.getLon() != null) {
                device.setLon(updateDevice.getLon());
            }
            
            if (updateDevice.getUsername() != null) {
                EndUser endUser = endUserRepository.findByUserUsername(updateDevice.getUsername())
                                         .orElseThrow(() -> new NotFoundException("User is not found!"));
                device.setEndUser(endUser);
            }

            // Update the user
            deviceRepository.updateIotDevice(device.getName(), device.getLat(), device.getLon(), device.getEndUser(), device.getId());

            log.info("User with id {} is updated", updateDevice.getUsername());

            return new RDevice(device);          
        } catch (NotFoundException e) {
            log.error("Device update failed since user or device does not exist");
            throw e;
        } catch (Exception e) {
            log.error("User update failed for device {}", updateDevice.getUsername(), e);
            throw new SomethingWentWrongException();
        }
    }
}
