package com.oris_spring.persistence.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private UUID id;
    private String name;
    private LocalDate birthDate;
}