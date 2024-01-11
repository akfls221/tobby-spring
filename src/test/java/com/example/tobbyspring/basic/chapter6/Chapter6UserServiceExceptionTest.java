package com.example.tobbyspring.basic.chapter6;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class Chapter6UserServiceExceptionTest {

    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Autowired
    Chapter6Service exceptionService;

    @BeforeEach
    void init() {
        User user1 = new User("엄태권1", "3345", Level.BASIC, 50, 10);
        User user2 = new User("엄태권2", "3345", Level.BASIC, 50, 10);
        User user3 = new User("엄태권3", "3345", Level.BASIC, 50, 10);

        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
    }

    @Test
    void upgradeAllOrNothing() {
        Chapter6ServiceImpl.ExceptionTestServiceImpl exceptionTestServiceImpl = new Chapter6ServiceImpl.ExceptionTestServiceImpl(userDao, 2L);
        Chapter6ServiceTxImpl chapter6ServiceTx = new Chapter6ServiceTxImpl(exceptionTestServiceImpl, platformTransactionManager);

        assertThatThrownBy(chapter6ServiceTx::upgradeLevels)
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.getAll())
                .extracting(User::getLevel)
                .containsOnly(Level.BASIC);
    }

    @Test
    void proxyFactoryBeanCreatorTest() {
        assertThatThrownBy(this.exceptionService::upgradeLevels)
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.getAll())
                .extracting(User::getLevel)
                .containsOnly(Level.BASIC);

        assertThat(this.exceptionService).isInstanceOf(Proxy.class);
    }

    @Test
    void readOnlyTransactionTest() {
        this.exceptionService.getAll();
    }

}
