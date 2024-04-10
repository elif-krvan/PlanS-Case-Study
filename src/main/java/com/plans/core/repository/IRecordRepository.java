package com.plans.core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plans.core.model.Location;
import com.plans.core.model.DeviceRecord;
import com.plans.core.model.composite_id.RecordId;

@Repository
public interface IRecordRepository extends JpaRepository<DeviceRecord, RecordId> {
    Optional<DeviceRecord> findByDeviceId(UUID id);
}