package com.javabeast.udp;

import com.javabeast.ampq.MessageParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.io.encoding.StandardCodecs;
import reactor.net.netty.udp.NettyDatagramServer;
import reactor.net.udp.DatagramServer;
import reactor.net.udp.spec.DatagramServerSpec;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jeffreya on 05/11/2016.
 *
 */

@Configuration
public class Listener {

    private Log log = LogFactory.getLog(Listener.class);

    @Value("${server.port}")
    private String port;


    @Autowired
    private MessageParser messageParser;

    @Autowired
    private Binding binding;

    @Bean
    public DatagramServer<byte[], byte[]> datagramServer(Environment env) throws InterruptedException {
        final DatagramServer<byte[], byte[]> server = new DatagramServerSpec<byte[], byte[]>(NettyDatagramServer.class)
                .env(env)
                .listen(Integer.valueOf(port))
                .codec(StandardCodecs.BYTE_ARRAY_CODEC)
                .consumeInput(messageParser::parseMessage)
                .get();
        server.start().await();
        return server;
    }
    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);
    }
}