package com.oris_spring.service;


import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.oris_spring.persistence.entity.UserEntity;
import com.oris_spring.persistence.repository.InMemoryRepository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryRepository repository;
    // это антипаттерн, круто, когда service является stateless!
    @Value("${logging.enabled}")
    private Boolean isLoggingEnabled;

    public void save(String name, LocalDate birthDate) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        UserEntity user = UserEntity.builder()
                .name(name)
                .birthDate(birthDate)
                .build();

        repository.save(user);

        if (isLoggingEnabled) {
            System.out.println("new user saved");
        }
    }

    public UserEntity get(UUID id) {
        return repository.get(id);
    }
}
