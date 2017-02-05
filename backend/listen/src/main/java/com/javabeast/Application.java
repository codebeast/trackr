package com.javabeast;

import com.javabeast.ampq.AMPQService;
import com.javabeast.udp.Listener;
import com.javabeast.udp.QuoteOfTheMomentServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import reactor.spring.context.config.EnableReactor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jeffreya on 05/11/2016.
 */

@SpringBootApplication
@EnableReactor
@Import({Listener.class, AMPQService.class})
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new QuoteOfTheMomentServerHandler());

            b.bind(5000).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
        final ApplicationContext ctx = SpringApplication.run(Application.class);
        final CountDownLatch latch = ctx.getBean(CountDownLatch.class);
        latch.await();


    }

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(2);
    }
}