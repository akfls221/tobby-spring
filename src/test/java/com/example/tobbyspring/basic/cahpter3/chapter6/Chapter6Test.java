package com.example.tobbyspring.basic.cahpter3.chapter6;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chpter5.MockMailSender;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Chapter6Test {

    @Autowired
    Chapter6Service chapter6Service;
    @Autowired
    Chapter6ServiceImpl chapter6ServiceImpl;
    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager platformTransactionManager;


    @Test
    void upgradeLevel() {
        MockMailSender mailSender = new MockMailSender();
        chapter6ServiceImpl.setMailSender(mailSender);

        User mockUser = createMockUser();
        mockUser.setLevel(Level.BASIC);
        mockUser.setLogin(50);

        User mockUser2 = createMockUser();
        mockUser2.setLevel(Level.SILVER);
        mockUser2.setRecommend(30);

        User mockUser3 = createMockUser();
        mockUser3.setLevel(Level.GOLD);

        userDao.add(mockUser);
        userDao.add(mockUser2);
        userDao.add(mockUser3);

        chapter6Service.upgradeLevels();

        assertThat(userDao.get(1L).getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get(2L).getLevel()).isEqualTo(Level.GOLD);
        assertThat(userDao.get(3L).getLevel()).isEqualTo(Level.GOLD);

        assertThat(mailSender.getRequests().get(0)).isEqualTo(mockUser.getName());
        assertThat(mailSender.getRequests().get(1)).isEqualTo(mockUser2.getName());
    }

    private User createMockUser() {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("3345");
        return user;
    }
}
