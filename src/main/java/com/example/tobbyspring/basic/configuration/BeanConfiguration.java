package com.example.tobbyspring.basic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BeanConfiguration {

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager();
    }
}
