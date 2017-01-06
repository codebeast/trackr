package com.javabeast;

import com.javabeast.ampq.AMPQService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.concurrent.CountDownLatch;


@SpringBootApplication
@Import({AMPQService.class})
//@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) throws InterruptedException {
        final ApplicationContext ctx = SpringApplication.run(Application.class);
    }

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(2);
    }

}