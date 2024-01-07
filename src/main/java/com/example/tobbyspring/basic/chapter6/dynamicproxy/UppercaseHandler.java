package com.example.tobbyspring.basic.chapter6.dynamicproxy;

import com.example.tobbyspring.basic.chapter6.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    private Hello target;

    public UppercaseHandler(Hello target) {
        this.target = target;
    }

    //타킷의 meothod를 호출함
    @Override
    public String invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = (String) method.invoke(target, args);
        return result.toUpperCase();
    }
}
