package com.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hr")
public class HRApplication {
    public static void main(String[] args) {
        SpringApplication.run(HRApplication.class, args);
    }
}