package com.javabeast;

import com.javabeast.ampq.AMPQConnection;
import com.javabeast.caching.Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
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
@Import({AMPQConnection.class, Cache.class})
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
public class Application {

    public static void main(String[] args) throws InterruptedException {
        final ApplicationContext ctx = SpringApplication.run(Application.class);
        final CountDownLatch latch = ctx.getBean(CountDownLatch.class);
        latch.await();
    }
}