package com.example.tobbyspring;

import com.example.tobbyspring.basic.chapter1.step1.UserDao;
import com.example.tobbyspring.entity.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDao();

        User user = new User();
        user.setId("owen");
        user.setName("엄태권");
        user.setPassword("password");

        userDao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getPassword() + "조회 성공");

    }
}
