package com.example.tobbyspring.basic.configuration;

import com.example.tobbyspring.basic.chpter5.MockMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class BeanConfig {

    @Bean
    public MailSender mailSender() {
        return new MockMailSender();
    }
}
