package com.example.tobbyspring.basic.chapter6.proxyTest;

import com.example.tobbyspring.basic.chapter6.Hello;

public class HelloTarget implements Hello {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankYou(String name) {
        return "ThankYou " + name;
    }
}
