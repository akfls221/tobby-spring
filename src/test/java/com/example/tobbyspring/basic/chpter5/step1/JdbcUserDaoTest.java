package com.example.tobbyspring.basic.chpter5.step1;

import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class JdbcUserDaoTest {

    @Autowired
    JdbcUserDao jdbcUserDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcUserDao.deleteAll();
        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    void add() {
        User testUser = createTestUser();

        jdbcUserDao.add(testUser);

        int actual = jdbcUserDao.getCount();

        assertThat(actual).isOne();
    }

    @Test
    void deleteAll() {
        jdbcUserDao.deleteAll();

        int actual = jdbcUserDao.getCount();

        assertThat(actual).isZero();
    }

    @Test
    void read() {
        User testUser = createTestUser();

        jdbcUserDao.add(testUser);

        User actual = jdbcUserDao.get(1L);

        assertAll(
                () -> assertThat(testUser.getName()).isEqualTo(actual.getName()),
                () -> assertThat(testUser.getPassword()).isEqualTo(actual.getPassword())
        );
    }

    private User createTestUser() {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("3345");
        return user;
    }

}
