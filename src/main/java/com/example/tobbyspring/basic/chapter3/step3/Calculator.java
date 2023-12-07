package com.example.tobbyspring.basic.chapter3.step3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        return this.template(path,
                bufferedReader -> {
                    int sum = 0;
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        sum += Integer.valueOf(line);
                    }

                    return sum;
                });
    }

    private int template(String path, Strategy strategy) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            return strategy.calcStrategy(bufferedReader);
        } catch (IOException e) {
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
