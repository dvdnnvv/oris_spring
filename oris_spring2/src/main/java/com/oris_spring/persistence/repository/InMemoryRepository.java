package com.oris_spring.persistence.repository;

import org.springframework.stereotype.Repository;
import com.oris_spring.persistence.entity.UserEntity;

import java.util.*;

@Repository
public class InMemoryRepository {
    private final Map<UUID, UserEntity> userMap = new HashMap<>();

    public void save(UserEntity user) {
        if (Objects.isNull(user.getId())) {
            user.setId(UUID.randomUUID());
            userMap.put(user.getId(), user);
        } else {
            Optional.ofNullable(userMap.get(user.getId()))
                    .map(existed -> {
                        existed.setName(user.getName());
                        existed.setBirthDate(user.getBirthDate());
                        return existed;
                    })
                    .orElse(userMap.put(user.getId(), user));
        }
    }

    public UserEntity get(UUID id) {
        return userMap.getOrDefault(id, null);
    }

    public boolean delete(UUID id) {
        return !Objects.isNull(userMap.remove(id));
    }
}