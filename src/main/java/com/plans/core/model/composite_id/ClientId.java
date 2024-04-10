package com.plans.core.model.composite_id;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientId implements Serializable {
    private UUID userId; // Primary key of the User entity
}