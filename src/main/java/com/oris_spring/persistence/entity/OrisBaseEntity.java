package com.oris_spring.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@MappedSuperclass
public abstract class OrisBaseEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "creation_dt", nullable = false)
    LocalDateTime creationDate;

    @Column(name = "last_action_dt", nullable = false)
    LocalDateTime lastActionDt;

    @PrePersist
    protected  void prePersist(){
        if (Objects.isNull(id)){
            setId(UUID.randomUUID());
        }

        LocalDateTime now = LocalDateTime.now();
        setCreationDate(now);
        setLastActionDt(now);
    }
    @PreUpdate
    protected void preUpdate(){
        setLastActionDt(LocalDateTime.now());
    }


}
