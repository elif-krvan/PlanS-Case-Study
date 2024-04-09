package com.plans.core.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plans.core.request.QUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank //TODO is it necessary for db stuff
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email field must be in a valid email format")
    private String email;

    @JsonIgnore
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    public User(QUser reqUser) {
        this.id = reqUser.getId();
        this.username = reqUser.getUsername();
        this.email = reqUser.getEmail();
        this.password = reqUser.getPassword();
    }
}
