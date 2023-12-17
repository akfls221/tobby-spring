package com.example.tobbyspring.basic.chpter5.step1;

import com.example.tobbyspring.entity.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(Long id);
    List<User> getAll();
    void deleteAll();
    int getCount();

}
