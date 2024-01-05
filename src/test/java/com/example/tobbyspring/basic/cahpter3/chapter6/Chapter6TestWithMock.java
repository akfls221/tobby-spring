package com.example.tobbyspring.basic.cahpter3.chapter6;

import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chpter5.MockMailSender;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter6TestWithMock {
    @Test
    void upgradeLevel() {
        Chapter6ServiceImpl.MockUserDao mockUserDao = new Chapter6ServiceImpl.MockUserDao(createMockUsers());
        MockMailSender mockMailSender = new MockMailSender();
        Chapter6ServiceImpl chapter6Service = new Chapter6ServiceImpl(mockUserDao);
        chapter6Service.setMailSender(mockMailSender);

        chapter6Service.upgradeLevels();

        List<User> updatedUsers = mockUserDao.getUpdated();
        assertThat(updatedUsers.get(0).getLevel()).isEqualTo(Level.SILVER);
        assertThat(updatedUsers.get(1).getLevel()).isEqualTo(Level.GOLD);

        List<String> mailSends = mockMailSender.getRequests();
        assertThat(mailSends.get(0)).isEqualTo(updatedUsers.get(0).getName());
        assertThat(mailSends.get(1)).isEqualTo(updatedUsers.get(1).getName());
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
