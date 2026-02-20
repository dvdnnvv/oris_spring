package ru.itis;

import ru.itis.config.AppConfig;
import ru.itis.entity.User;
import ru.itis.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);


        userService.createUser("тимурка");
        userService.createUser("линочка");
        userService.createUser("лисос");


        System.out.println("все пользователи:");
        for (User user : userService.getAllUsers()) {
            System.out.println(user.getId() + ": " + user.getName());
        }


        userService.updateUser(2L, "лина тимуровна");

        System.out.println("пользователь с id 2: " + userService.getUserById(2L).getName());

        userService.deleteUser(3L);

        System.out.println("после удаления:");
        for (User user : userService.getAllUsers()) {
            System.out.println(user.getId() + ": " + user.getName());
        }
    }
}