// package com.plans.core.repository;

// import java.util.UUID;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.plans.core.model.IoTDevice;

// @Repository
// public interface IDeviceRepository extends JpaRepository<IoTDevice, UUID> {
//     Optional<IoTDevice> findByEndUserUserId(UUID id);
// }