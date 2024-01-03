package com.example.tobbyspring.basic;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceException {

    private final UserDao userDao;
    private final PlatformTransactionManager transactionManager;


    public void upgradeLevels() {
        TransactionStatus transaction = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();

            for (int i = 0; i < users.size(); i++) {
                if (i == 2) {
                    throw new RuntimeException();
                }

                if (canUpgradeLevel(users.get(i))) {
                    users.get(i).upgradeLevel();
                    userDao.update(users.get(i));
                }
            }

            this.transactionManager.commit(transaction);
        } catch (RuntimeException e) {
            this.transactionManager.rollback(transaction);
            throw e;
        }
    }

    private boolean canUpgradeLevel(User user) {
        Level level = user.getLevel();

        return switch (level) {
            case GOLD -> false;
            case BASIC -> user.getLogin() >= 50;
            case SILVER -> user.getRecommend() >= 30;
        };
    }

}
