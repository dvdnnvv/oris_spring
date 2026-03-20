package com.oris_spring.api;

import com.oris_spring.persistence.entity.UserEntity;
import com.oris_spring.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String getUsersPage() {
        return "index";
    }

    @GetMapping("/users/v2")
    public String getUsersPageV2() {
        return "index";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id,
                              @RequestParam("name") Integer age,
                              @RequestHeader("content-type") String contentType,
                              @CookieValue("sessionID") String sessionId,
                              @RequestBody String requestBody) {
        UserEntity userEntity = userService.get(id);
        return "index";
    }

    @PostMapping("/users/new")
    public String saveNewUser(@RequestBody String login) {
        userService.save(login, LocalDate.now());
        return "index";
    }
}