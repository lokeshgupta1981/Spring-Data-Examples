package com.howtodoinjava.records;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.howtodoinjava.records")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}