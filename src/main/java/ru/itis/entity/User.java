package ru.itis.entity;

public class User {
    private Long id;
    private String name;

    public User (String name){
        this.name = name;

    }
    public User(Long id, String name){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
