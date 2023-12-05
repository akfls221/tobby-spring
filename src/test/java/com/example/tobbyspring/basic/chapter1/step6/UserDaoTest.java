package com.example.tobbyspring.basic.chapter1.step6;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {
//https://spring.io/blog/2023/06/23/improved-testcontainers-support-in-spring-boot-3-1
    //https://spring.io/blog/2023/06/19/spring-boot-31-connectiondetails-abstraction

}
