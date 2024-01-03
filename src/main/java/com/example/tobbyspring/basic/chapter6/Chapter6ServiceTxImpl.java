package com.example.tobbyspring.basic.chapter6;

import com.example.tobbyspring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RequiredArgsConstructor
public class Chapter6ServiceTxImpl implements Chapter6Service{

    private final Chapter6Service chapter6Service;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void add(User user) {
        chapter6Service.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus transaction = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            chapter6Service.upgradeLevels();

            this.transactionManager.commit(transaction);
        } catch (RuntimeException e) {
            this.transactionManager.rollback(transaction);
            throw e;
        }
    }
}
