package com.example.tobbyspring.basic.chapter6.transactionproxy;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TxProxyFactoryBeanTest {

    @Autowired
    ApplicationContext context;
    @Autowired
    MailSender mailSender;
    @Autowired
    UserDao userDao;

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
    @DirtiesContext
    void upgradeAllOrNothing() {
        Chapter6ServiceImpl.ExceptionTestService target = new Chapter6ServiceImpl.ExceptionTestService(this.userDao, 2L);
        target.setMailSender(this.mailSender);

        TxProxyFactoryBean txProxyFactoryBean = this.context.getBean("&userService2", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(target);

        Chapter6Service userService = (Chapter6Service) txProxyFactoryBean.getObject();

        assertThatThrownBy(userService::upgradeLevels)
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.getAll())
                .extracting(User::getLevel)
                .containsOnly(Level.BASIC);
    }

    @Test
    @DirtiesContext
    void proxyFactoryBeanTest() {
        Chapter6ServiceImpl.ExceptionTestService target = new Chapter6ServiceImpl.ExceptionTestService(this.userDao, 2L);
        target.setMailSender(this.mailSender);

        ProxyFactoryBean proxyFactoryBean = this.context.getBean("&userServiceWithAdvisor", ProxyFactoryBean.class);
        proxyFactoryBean.setTarget(target);

        Chapter6Service userService = (Chapter6Service) proxyFactoryBean.getObject();

        assertThatThrownBy(userService::upgradeLevels)
                .isInstanceOf(RuntimeException.class);

        assertThat(userDao.getAll())
                .extracting(User::getLevel)
                .containsOnly(Level.BASIC);
    }
}
