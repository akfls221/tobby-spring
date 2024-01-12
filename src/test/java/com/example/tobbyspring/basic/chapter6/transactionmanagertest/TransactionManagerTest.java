package com.example.tobbyspring.basic.chapter6.transactionmanagertest;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@SpringBootTest
public class TransactionManagerTest {

    @Autowired
    Chapter6Service chapter6ServiceImpl;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    void transactionSync() {
        chapter6ServiceImpl.deleteAll();

        chapter6ServiceImpl.add(createMockUsers().get(0));
        chapter6ServiceImpl.add(createMockUsers().get(1));
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
