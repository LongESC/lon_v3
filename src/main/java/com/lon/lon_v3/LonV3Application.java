package com.lon.lon_v3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@EnableCaching
@SpringBootApplication
public class LonV3Application {

    public static void main(String[] args) {
        SpringApplication.run(LonV3Application.class, args);
    }

}
