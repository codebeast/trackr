package com.javabeast;

import com.javabeast.ampq.Writer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import reactor.spring.context.config.EnableReactor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jeffreya on 05/11/2016.
 *
 */

@SpringBootApplication
@EnableReactor
@Import({Writer.class})
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(Application.class);
        CountDownLatch latch = ctx.getBean(CountDownLatch.class);
        latch.await();
    }
}