package com.example.tobbyspring.basic.chapter6;

import com.example.tobbyspring.entity.User;

import java.util.List;

public interface Chapter6Service {

    void add(User user);

    void upgradeLevels();

    User get(Long id);

    List<User> getAll();

    void deleteAll();

    void update(User user);
}
