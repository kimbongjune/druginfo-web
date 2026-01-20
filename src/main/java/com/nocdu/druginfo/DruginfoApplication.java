package com.nocdu.druginfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DruginfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruginfoApplication.class, args);
    }
}
