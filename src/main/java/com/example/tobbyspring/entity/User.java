package com.example.tobbyspring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String password;
    private Level level;
    int login;
    int recommend;

    public User(Long id, String name, String password, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public User(String name, String password, Level level, int login, int recommend) {
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public User() {
    }

    public void upgradeLevel() {
        if (this.level.nextLevel() == null) {
            throw new IllegalArgumentException("현재 레벨은 업그레이드 할 수 없는 레벨 입니다. currenLevel : " + this.level);
        }

        this.level = this.level.nextLevel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }
}
