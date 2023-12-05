package com.example.tobbyspring.basic.chapter1.step6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ConnectionFactory {

    @Bean
    public UserDao_5 userDao() {
        return new UserDao_5(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(nUserConnection());
    }

    @Bean
    public ConnectionMaker nUserConnection() {
        return new NConnectionMaker();
    }
}
