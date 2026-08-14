package com.jurisai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AccountService {
    public static void main(String[] args) {

        SpringApplication.run(AccountService.class);
    }
}