package com.example.tobbyspring.basic.chpter5;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceExceptionTest {

    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
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
        UserServiceException userServiceException = new UserServiceException(userDao, platformTransactionManager);

        assertThatThrownBy(userServiceException::upgradeLevels)
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.getAll())
                .extracting(User::getLevel)
                .containsOnly(Level.BASIC);
    }

}
