package com.example.tobbyspring.basic;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PlatformTransactionManager platformTransactionManager;
    private MailSender mailSender;

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void upgradeLevels() {
        TransactionStatus transaction = this.platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();

            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    user.upgradeLevel();
                    userDao.update(user);
                    sendUpgradeEmail(user);
                }
            }

            this.platformTransactionManager.commit(transaction);
        } catch (RuntimeException e) {
            this.platformTransactionManager.rollback(transaction);
            throw e;
        }
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getName());
        message.setFrom("test@spring.com");
        message.setSubject("Upgrade 안내");
        message.setText("사용자님의 등급이 " + user.getLevel() + "로 상승했습니다.");

        this.mailSender.send(message);
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
