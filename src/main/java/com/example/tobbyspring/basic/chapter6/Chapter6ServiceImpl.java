package com.example.tobbyspring.basic.chapter6;

import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import com.example.tobbyspring.entity.Level;
import com.example.tobbyspring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
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

    @Override
    public User get(Long id) {
        return this.userDao.get(id);
    }

    @Override
    public List<User> getAll() {
        return this.userDao.getAll();
    }

    @Override
    public void deleteAll() {
        this.userDao.deleteAll();
    }

    @Override
    public void update(User user) {
        this.userDao.update(user);
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

    public static class ExceptionTestServiceImpl extends Chapter6ServiceImpl {

        private Long id = 3L;

        public ExceptionTestServiceImpl(UserDao userDao, Long id) {
            super(userDao);
            this.id = id;
        }

        public ExceptionTestServiceImpl(UserDao userDao) {
            super(userDao);
        }

        @Override
        public void setMailSender(MailSender mailSender) {
            super.setMailSender(mailSender);
        }

        @Override
        protected void upgradeLevel(User user) {
            if (Objects.equals(user.getId(), this.id)) {
                throw new IllegalArgumentException("error");
            }
            super.upgradeLevel(user);
        }

        @Override
        public List<User> getAll() {
            for (User user : super.getAll()) {
                super.update(user); //현재 get*에는 읽기 전용 속성이(BeanConfig의 transactionInterceptor() 확인) 걸려있기 때문에 오류가발생함
            }
            return null;
        }
    }

    public static class MockUserDao implements UserDao {

        private List<User> users; // 레벨 업그레이드 후보 User 오브젝트 목록
        private List<User> updated = new ArrayList<>(); //업그레이드 대상 오브젝트를 저장해둘 목록

        public MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return updated;
        }

        @Override
        public List<User> getAll() {
            return this.users;
        }

        @Override
        public void update(User user) {
            this.updated.add(user);
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
