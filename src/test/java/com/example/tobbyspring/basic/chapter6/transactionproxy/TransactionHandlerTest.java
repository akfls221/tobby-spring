package com.example.tobbyspring.basic.chapter6.transactionproxy;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TransactionHandlerTest {

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    UserDao userDao;

    @Autowired
    MailSender mailSender;

    @BeforeEach
    void init() {
        User user1 = new User("엄태권1", "3345", Level.BASIC, 50, 10);
        User user2 = new User("엄태권2", "3345", Level.BASIC, 50, 10);
        User user3 = new User("엄태권3", "3345", Level.BASIC, 50, 10);

        this.userDao.add(user1);
        this.userDao.add(user2);
        this.userDao.add(user3);
    }

    @Test
    void upgradeLevel() {
        Chapter6ServiceImpl.ExceptionTestServiceImpl target = new Chapter6ServiceImpl.ExceptionTestServiceImpl(this.userDao, 2L);
        target.setMailSender(this.mailSender);
        TransactionHandler proxyObject = new TransactionHandler(target, this.platformTransactionManager, "upgradeLevels");

        Chapter6Service userService = (Chapter6Service) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[]{Chapter6Service.class}, proxyObject);

        assertThatThrownBy(userService::upgradeLevels)
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.getAll())
                .extracting(User::getLevel)
                .containsOnly(Level.BASIC);
    }

    private List<User> createMockUsers() {
        User mockUser = createMockUser();
        mockUser.setLevel(Level.BASIC);
        mockUser.setLogin(50);

        User mockUser2 = createMockUser();
        mockUser2.setLevel(Level.SILVER);
        mockUser2.setRecommend(30);

        User mockUser3 = createMockUser();
        mockUser3.setLevel(Level.GOLD);

        return List.of(mockUser, mockUser2, mockUser3);
    }

    private User createMockUser() {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("3345");
        return user;
    }

}
