package com.liu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class VueBackApplication {


    public static void main(String[] args) {
        SpringApplication.run(VueBackApplication.class, args);
    }


}
