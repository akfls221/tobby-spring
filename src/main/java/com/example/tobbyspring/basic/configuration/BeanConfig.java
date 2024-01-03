package com.example.tobbyspring.basic.configuration;

import com.example.tobbyspring.basic.chapter6.Chapter6Service;
import com.example.tobbyspring.basic.chapter6.Chapter6ServiceImpl;
import com.example.tobbyspring.basic.chapter6.Chapter6ServiceTxImpl;
import com.example.tobbyspring.basic.chpter5.MockMailSender;
import com.example.tobbyspring.basic.chpter5.step1.JdbcUserDao;
import com.example.tobbyspring.basic.chpter5.step1.UserDao;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BeanConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem://localhost/~/tobby-spring;DB_CLOSE_ON_EXIT=FALSE")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UserDao userDao() {
        return new JdbcUserDao(jdbcTemplate());
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MailSender mailSender() {
        return new MockMailSender();
    }

    @Bean
    public Chapter6Service chapter6Service() {
        return new Chapter6ServiceTxImpl(chapter6ServiceImpl(), platformTransactionManager());
    }

    @Bean
    public Chapter6ServiceImpl chapter6ServiceImpl() {
        return new Chapter6ServiceImpl(userDao());
    }
}
