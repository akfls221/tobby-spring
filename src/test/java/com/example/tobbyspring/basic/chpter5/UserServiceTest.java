package com.example.tobbyspring.basic.chpter5;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserDao userDao;

    @Test
    void upgradeLevel() {
        User mockUser = createMockUser();
        mockUser.setLevel(Level.BASIC);
        mockUser.setLogin(50);

        User mockUser2 = createMockUser();
        mockUser2.setLevel(Level.SILVER);
        mockUser2.setRecommend(30);

        User mockUser3 = createMockUser();
        mockUser3.setLevel(Level.GOLD);

        when(userDao.getAll()).thenReturn(List.of(mockUser, mockUser2, mockUser3));
        doNothing().when(userDao).update(any());

        userService.upgradeLevels();

        assertThat(mockUser.getLevel()).isEqualTo(Level.SILVER);
        assertThat(mockUser2.getLevel()).isEqualTo(Level.GOLD);
        assertThat(mockUser3.getLevel()).isEqualTo(Level.GOLD);
    }

    private User createMockUser() {
        User user = new User();
        user.setName("엄태권");
        user.setPassword("3345");
        return user;
    }
}
