package com.plans.core.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plans.core.enums.Role;
import com.plans.core.model.User;

@Repository
public interface IAccountRepository extends JpaRepository<User, UUID> {
    List<User> findAllById(UUID id);
    List<User> findAllByRole(Role role);

    Optional<User> findUserById(UUID id);
    Optional<User> findByEmailAndRole(String email, Role role);
    boolean existsByEmailAndRole(String email, Role role);    

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String email);

    int deleteByUsername(String username);

    @Modifying
    // @Transactional
    @Query("UPDATE User u SET u.username = :username, u.email = :email, u.password = :password WHERE u.id = :userId")
    int updateUserInfo(String username, String email, String password, UUID userId);
}