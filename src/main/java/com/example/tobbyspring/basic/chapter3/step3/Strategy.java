package com.example.tobbyspring.basic.chapter3.step3;

import java.io.BufferedReader;
import java.io.IOException;

public interface Strategy {

    int calcStrategy(BufferedReader bufferedReader) throws IOException;
}
