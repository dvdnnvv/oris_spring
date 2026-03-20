package com.oris_spring.persistence.repository;

import com.oris_spring.persistence.entity.TestEntity;
import com.oris_spring.persistence.entity.UserEntity;
import com.oris_spring.persistence.entity.UserStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void savePersistsEnumAndOneToManyRelation() {
        UserEntity user = UserEntity.builder()
                .name("Timur")
                .birthDate(LocalDate.of(2000, 1, 1))
                .status(UserStatus.BLOCKED)
                .build();

        user.addTest(TestEntity.builder().build());

        UserEntity saved = repository.save(user);
        entityManager.flush();
        entityManager.clear();

        UserEntity loaded = repository.findById(saved.getId()).orElseThrow();

        assertNotNull(loaded.getId());
        assertEquals(UserStatus.BLOCKED, loaded.getStatus());
        assertFalse(loaded.getTests().isEmpty());
    }

    @Test
    void findAllByBirthDateBetweenReturnsExpectedUsers() {
        repository.save(UserEntity.builder()
                .name("A")
                .birthDate(LocalDate.of(1999, 2, 2))
                .status(UserStatus.ACTIVE)
                .build());

        repository.save(UserEntity.builder()
                .name("B")
                .birthDate(LocalDate.of(2012, 2, 2))
                .status(UserStatus.INACTIVE)
                .build());

        List<UserEntity> users = repository.findAllByBirthDateBetween(
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2005, 1, 1)
        );

        assertEquals(1, users.size());
        assertEquals("A", users.getFirst().getName());
    }
}


