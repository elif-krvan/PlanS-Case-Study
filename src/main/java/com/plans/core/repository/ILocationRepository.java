package com.plans.core.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plans.core.model.EndUser;
import com.plans.core.model.IoTDevice;
import com.plans.core.model.Location;
import com.plans.core.model.composite_id.LocationId;

@Repository
public interface ILocationRepository extends JpaRepository<Location, LocationId> {
    Optional<Location> findByLatAndLon(Double lat, Double lon);
}