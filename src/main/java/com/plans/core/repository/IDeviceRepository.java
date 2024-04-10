package com.plans.core.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plans.core.model.EndUser;
import com.plans.core.model.IoTDevice;

@Repository
public interface IDeviceRepository extends JpaRepository<IoTDevice, UUID> {
    List<IoTDevice> findByEndUserUserUsername(String username);

    @Modifying
    @Query("UPDATE IoTDevice d SET d.name = :name, d.lat = :lat, d.lon = :lon, d.endUser = :endUser WHERE d.id = :id")
    int updateIotDevice(String name, Double lat, Double lon, EndUser endUser, UUID id);
}