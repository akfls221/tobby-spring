package com.example.tobbyspring.basic.configuration;

import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestDbConfiguration {

    @Bean
    public JdbcConnectionDetails testJdbcConnectionDetails() {
        return new JdbcConnectionDetails() {
            @Override
            public String getUsername() {
                return "test";
            }

            @Override
            public String getPassword() {
                return "test";
            }

            @Override
            public String getJdbcUrl() {
                return "jdbc:tc:mysql:5.7.34://test_db";
            }
        };
    }
}
