package com.example.tobbyspring.basic.chpter5.step1;

import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class JdbcChapter1UserDaoTest {

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
                () -> assertThat(testUser.getPassword()).isEqualTo(actual.getPassword()),
                () -> assertThat(testUser.getLevel()).isEqualTo(actual.getLevel()),
                () -> assertThat(testUser.getLogin()).isEqualTo(actual.getLogin()),
                () -> assertThat(testUser.getRecommend()).isEqualTo(actual.getRecommend())
        );
    }

    @Test
    void update() {
        User testUser = createTestUser();

        jdbcUserDao.add(testUser);

        User findUser = jdbcUserDao.get(1L);

        findUser.setName("change");
        findUser.setPassword("changePassword");
        findUser.setLevel(Level.GOLD);
        findUser.setLogin(3);
        findUser.setRecommend(999);
        jdbcUserDao.update(findUser);

        User updateUser = jdbcUserDao.get(findUser.getId());

        assertThat(findUser).isEqualTo(updateUser);
    }

    private User createTestUser() {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("3345");
        user.setLevel(Level.BASIC);
        user.setLogin(1);
        user.setRecommend(0);
        return user;
    }

}
