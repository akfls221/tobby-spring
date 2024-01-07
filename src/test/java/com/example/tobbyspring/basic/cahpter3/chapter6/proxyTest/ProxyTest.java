package com.example.tobbyspring.basic.cahpter3.chapter6.proxyTest;

import com.example.tobbyspring.basic.chapter6.proxyTest.Hello;
import com.example.tobbyspring.basic.chapter6.proxyTest.HelloTarget;
import com.example.tobbyspring.basic.chapter6.proxyTest.HelloUppercase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProxyTest {

    @Test
    void simpleProxy() {
        Hello hello = new HelloTarget();

        assertAll(
                () -> assertThat(hello.sayHello("owen")).isEqualTo("Hello owen"),
                () -> assertThat(hello.sayHi("owen")).isEqualTo("Hi owen"),
                () -> assertThat(hello.sayThankYou("owen")).isEqualTo("ThankYou owen")
        );
    }

    @Test
    void proxyHello() {
        Hello hello = new HelloUppercase(new HelloTarget());

        assertAll(
                () -> assertThat(hello.sayHello("owen")).isEqualTo("HELLO OWEN"),
                () -> assertThat(hello.sayHi("owen")).isEqualTo("HI OWEN"),
                () -> assertThat(hello.sayThankYou("owen")).isEqualTo("THANKYOU OWEN")
        );
    }
}
