package com.plans.core.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.plans.core.model.composite_id.ClientId;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "end_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @IdClass(EndUserId.class)
public class EndUser {
    @Id
    @NotNull
    private UUID userId; // maps to User.id by @MapsId

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(),
        nullable = false
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    // @OneToMany(mappedBy = "endUser") // TODO what does this do
    // private List<IoTDevice> devices;
}
