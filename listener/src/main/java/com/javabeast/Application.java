package com.javabeast;

import com.javabeast.udpserver.UDPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import reactor.spring.context.config.EnableReactor;

import java.util.concurrent.CountDownLatch;

@Configuration
@EnableAutoConfiguration
@EnableReactor
@ComponentScan
@Import(UDPServer.class)
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(Application.class);
        CountDownLatch latch = ctx.getBean(CountDownLatch.class);
        latch.await();
    }
}