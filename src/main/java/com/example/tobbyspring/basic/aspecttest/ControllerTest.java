package com.example.tobbyspring.basic.aspecttest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/")
    public String aopTest() {
        return "callAop";
    }
}
