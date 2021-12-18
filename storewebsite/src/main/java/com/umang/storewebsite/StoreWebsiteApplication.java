package com.umang.storewebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
public class StoreWebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreWebsiteApplication.class, args);
    }
}
