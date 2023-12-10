package com.example.tobbyspring.basic.chapter3.step4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        return this.template(path, 0,
                (line, initValue) -> initValue + Integer.parseInt(line)
        );
    }

    public int calcMultiply(String path) throws IOException {
        return this.template(path, 1,
                (line, initValue) -> initValue * Integer.parseInt(line)
        );
    }

    private int template(String path, int initValue, Strategy strategy) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));

            int res = initValue;
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                res = strategy.doSomeThingWithLine(line, res);
            }

            return res;
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
