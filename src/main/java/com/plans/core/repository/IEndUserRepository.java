package com.plans.core.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plans.core.model.EndUser;

@Repository
public interface IEndUserRepository extends JpaRepository<EndUser, UUID> {
    // List<EndUser> findAllById(UUID id);
    // Optional<EndUser> findByUserId(UUID id);
    // Optional<EndUser> findByUserEmail(String email);
}