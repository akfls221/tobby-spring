package com.example.tobbyspring.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @ParameterizedTest(name = "업그레이드가 가능한 레벨인 {0} 의 경우 각각의 다음 레벨은 {1}이다.")
    @CsvSource(value = {"BASIC, SILVER", "SILVER, GOLD"})
    void upgradeLevel(Level level, Level expected) {
        User user = new User(1L, "test", "test", level, 1, 2);

        user.upgradeLevel();

        assertThat(user.getLevel()).isEqualTo(expected);
    }

}
