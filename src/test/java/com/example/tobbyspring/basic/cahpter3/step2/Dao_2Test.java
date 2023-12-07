package com.example.tobbyspring.basic.cahpter3.step2;

import com.example.tobbyspring.basic.chapter3.step2.Dao_2;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class Dao_2Test {

    @Autowired
    private Dao_2 userDao;

    @BeforeEach
    void setUp() throws SQLException {
        userDao.deleteAll();
    }

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

    @Test
    @DisplayName("User select Test")
    void read() throws SQLException {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("3345");

        userDao.add(user);

        User actual = userDao.get(1L);

        assertAll(
                () -> assertThat(user.getName()).isEqualTo(actual.getName()),
                () -> assertThat(user.getPassword()).isEqualTo(actual.getPassword())
        );
    }

    @Test
    @DisplayName("User Update Test")
    void update() throws SQLException{
        User user = new User();
        user.setName("엄태권");
        user.setPassword("5253");

        userDao.add(user);

        user.setId(1L);
        user.setName("update");
        user.setPassword("1234");

        int actual = userDao.update(user);

        assertThat(actual).isOne();
    }

    @Test
    @DisplayName("User Delete All Test")
    void deleteAll() throws SQLException {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("74532");

        userDao.add(user);
        userDao.deleteAll();

        int actual = userDao.userCount();

        assertThat(actual).isZero();
    }
}
