package com.javabeast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;


@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        final ApplicationContext ctx = SpringApplication.run(Application.class);
        final CountDownLatch latch = ctx.getBean(CountDownLatch.class);
        latch.await();
    }

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(10);
    }
}