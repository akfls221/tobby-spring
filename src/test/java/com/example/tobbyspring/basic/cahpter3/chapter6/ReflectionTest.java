package com.example.tobbyspring.basic.cahpter3.chapter6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    @Test
    void invokeMethodTest() throws Exception {
        String name = "Spring";
        Method lengthMethod = String.class.getMethod("length");
        Method charAtMethod = String.class.getMethod("charAt", int.class);

        Assertions.assertAll(
                () -> assertThat(name.length()).isEqualTo(6),
                () -> assertThat((Integer) lengthMethod.invoke(name)).isEqualTo(6),
                () -> assertThat(name.charAt(0)).isEqualTo('S'),
                () -> assertThat(charAtMethod.invoke(name, 0)).isEqualTo('S')
        );
    }
}
