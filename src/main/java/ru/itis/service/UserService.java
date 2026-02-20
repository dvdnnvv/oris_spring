package ru.itis.service;

import ru.itis.entity.User;
import ru.itis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String name) {
        return userRepository.save(new User(name));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateUser(Long id, String newName) {
        User user = userRepository.findById(id);
        user.setName(newName);
        userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}