package com.example.tobbyspring.basic.chapter1.step7;

import com.example.tobbyspring.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @DisplayName("User insert Test")
    void add() throws SQLException {
        User user = new User();

        user.setName("엄태권");
        user.setPassword("1234");

        userDao.add(user);

        int actual = userDao.userCount();

        assertThat(actual).isOne();
    }
}
