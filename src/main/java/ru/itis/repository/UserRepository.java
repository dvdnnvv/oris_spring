package ru.itis.repository;

import ru.itis.entity.User;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserRepository {
    private Map<Long, User> storage = new HashMap<>();
    private long currentId = 1;

    public User save(User user) {
        user.setId(currentId++);
        storage.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    public User findById(Long id) {
        return storage.get(id);
    }

    public void update(User user) {
        storage.put(user.getId(), user);
    }

    public void delete(Long id) {
        storage.remove(id);
    }
}