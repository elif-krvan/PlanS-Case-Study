package com.plans.core.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plans.core.model.DeviceRecord;
import com.plans.core.model.compositeid.RecordId;

@Repository
public interface IRecordRepository extends JpaRepository<DeviceRecord, RecordId> {
    Optional<DeviceRecord> findByDeviceId(UUID id);

    List<DeviceRecord> findByDeviceIdAndTimestampBetween(UUID id, LocalDateTime startTime, LocalDateTime endTime);
    List<DeviceRecord> findByDeviceEndUserUserUsernameAndTimestampBetween(String username, LocalDateTime startTime, LocalDateTime endTime);
}