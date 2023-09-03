package com.example.thejobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TheJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheJobsApplication.class, args);
    }


}
