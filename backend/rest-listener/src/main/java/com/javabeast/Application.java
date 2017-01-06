package com.javabeast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        final ApplicationContext ctx = SpringApplication.run(Application.class);
    }

}