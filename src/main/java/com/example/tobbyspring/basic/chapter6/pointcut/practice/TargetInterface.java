package com.example.tobbyspring.basic.chapter6.pointcut.practice;

public interface TargetInterface {

    void hello();

    void hello(String text);

    int minus(int a, int b) throws RuntimeException;

    int plus(int a, int b);
}
