package com.example.tobbyspring.basic.cahpter3.chapter6;

import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTestWithMockito {
    @Mock
    UserDao userDao;
    @Mock
    MailSender mailSender;

    @Test
    void upgradeLevel() {
        Chapter6ServiceImpl chapter6Service = new Chapter6ServiceImpl(this.userDao);
        chapter6Service.setMailSender(this.mailSender);

        List<User> mockUsers = createMockUsers();
        when(this.userDao.getAll()).thenReturn(mockUsers);

        chapter6Service.upgradeLevels();

        verify(this.userDao, times(2)).update(any(User.class));
        assertThat(mockUsers.get(0).getLevel()).isEqualTo(Level.SILVER);
        assertThat(mockUsers.get(1).getLevel()).isEqualTo(Level.GOLD);
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
