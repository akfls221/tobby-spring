package com.example.tobbyspring.basic.chapter6;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class Chapter6ServiceImpl implements Chapter6Service{

    private final UserDao userDao;
    private MailSender mailSender;

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void add(User user) {
        this.userDao.add(user);
    }

    @Override
    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            upgradeLevel(user);
        }
    }

    protected void upgradeLevel(User user) {
        if (canUpgradeLevel(user)) {
            user.upgradeLevel();
            userDao.update(user);
            sendUpgradeEmail(user);
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

    public static class ExceptionTestService extends Chapter6ServiceImpl {

        private Long id;

        public ExceptionTestService(UserDao userDao, Long id) {
            super(userDao);
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (Objects.equals(user.getId(), this.id)) {
                throw new IllegalArgumentException("error");
            }
            super.upgradeLevel(user);
        }
    }

    public static class MockUserDao implements UserDao {

        @Override
        public List<User> getAll() {
            return null;
        }

        @Override
        public void update(User user) {

        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(Long id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }
    }
}
