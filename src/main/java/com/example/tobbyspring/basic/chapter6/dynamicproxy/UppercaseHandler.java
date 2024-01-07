package com.example.tobbyspring.basic.chapter6.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    private Object target;

    public UppercaseHandler(Object target) {
        this.target = target;
    }

    //타킷의 meothod를 호출함
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);

        if (result instanceof String stringTypeResult && method.getName().startsWith("say")) {
            return stringTypeResult.toUpperCase();
        }

        return result;

    }
}
