package com.plans.core.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plans.core.model.User;

// @Qualifier("accountjpa")
@Repository
public interface IAccountRepository extends JpaRepository<User, UUID> {
    List<User> findAllById(UUID id);
    Optional<User> findUserById(UUID id);
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String email);
}