package com.example.tobbyspring.basic.chapter6.transactionproxy;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {

    private Object target;
    private PlatformTransactionManager transactionManager;
    private String patter;

    public TransactionHandler(Object target, PlatformTransactionManager platformTransactionManager, String patter) {
        this.target = target;
        this.transactionManager = platformTransactionManager;
        this.patter = patter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith(patter)) {
            return invokeInTransaction(method, args);
        }

        return method.invoke(target, args);
    }

    private Object invokeInTransaction(Method method, Object[] args) throws Throwable{
        TransactionStatus transaction = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object ret = method.invoke(target, args);
            this.transactionManager.commit(transaction);
            return ret;

        } catch (InvocationTargetException e) {
            this.transactionManager.rollback(transaction);
            throw e;
        }

    }
}
