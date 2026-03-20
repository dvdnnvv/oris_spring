package com.oris_spring.service;

import com.oris_spring.persistence.entity.UserStatus;
import com.oris_spring.persistence.repository.JpaUserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.oris_spring.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public void save(String name, LocalDate birthDate) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        UserEntity user = UserEntity.builder()
                .name(name)
                .birthDate(birthDate)
                .status(UserStatus.ACTIVE)
                .build();

        jpaUserRepository.save(user);
    }

    public UserEntity get(Long id) {
        return jpaUserRepository.findById(id).orElse(null);
    }

    public List<UserEntity> findByBirthDateRange(LocalDate from, LocalDate to) {
        return jpaUserRepository.findAllByBirthDateBetween(from, to);
    }
}
