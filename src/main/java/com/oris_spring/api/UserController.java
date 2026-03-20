package com.oris_spring.api;

import com.oris_spring.persistence.entity.UserEntity;
import com.oris_spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping
    public void saveUser(@RequestParam String name) {
        service.save(name, LocalDate.now());
    }
}
