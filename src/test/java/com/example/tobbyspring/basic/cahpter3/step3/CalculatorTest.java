package com.example.tobbyspring.basic.cahpter3.step3;

import com.example.tobbyspring.basic.chapter3.step3.Calculator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    @Test
    void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int actual = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());

        assertThat(actual).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int actual = calculator.calcMultiply(getClass().getResource("/numbers.txt").getPath());

        assertThat(actual).isEqualTo(24);
    }
}
