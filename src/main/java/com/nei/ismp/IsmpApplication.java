package com.nei.ismp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IsmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsmpApplication.class, args);
    }
}
