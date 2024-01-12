package com.example.tobbyspring.basic.chapter6.transactionmanagertest;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * h2 데이터 베이스에서 @Transactional(readOnly = true) 가 안먹히는 이유
 * 참조 : https://ssisksl77.gitlab.io/code-that-talks/220526-spring-transactional-readonly.html
 *
 * 책에서는 @TransactionConfiguration 을사용해 클래스 단계에서의 롤백 설정을 기본으로 정하고 롤백이 필요한 곳만 @Rollback을 주면 된다고 하지만
 * 현재 Spring Framework 4.2부터는 @Rollback클래스 수준에서 transactionManager사용하고있음.
 *
 * @TransactionConfiguration은 Deprecated 되었다.
 *
 * 참조 : https://docs.spring.io/spring-framework/docs/4.2.0.RC3_to_4.2.0.RELEASE/Spring%20Framework%204.2.0.RELEASE/org/springframework/test/context/transaction/TransactionConfiguration.html
 */
@SpringBootTest
@Rollback(value = false)
class TransactionManagerTest {

    @Autowired
    Chapter6Service chapter6ServiceImpl;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    UserDao userDao;

    @Test
    void transactionSync() {
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(txDefinition);

        chapter6ServiceImpl.deleteAll();

        chapter6ServiceImpl.add(createMockUsers().get(0));
        chapter6ServiceImpl.add(createMockUsers().get(1));

        transactionManager.commit(transaction);
    }

    @Test
    void transactionRollBak() {
        chapter6ServiceImpl.deleteAll();
        Assertions.assertThat(userDao.getCount()).isZero();

        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(txDefinition);

        chapter6ServiceImpl.add(createMockUsers().get(0));
        chapter6ServiceImpl.add(createMockUsers().get(1));

        transactionManager.rollback(transaction);

        Assertions.assertThat(userDao.getCount()).isZero();
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
