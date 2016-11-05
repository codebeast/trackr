package com.javabeast.ampq;

import com.javabeast.listen.UDPListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jeffreya on 05/11/2016.
 *
 */

@Component
public class Receiver {

    private Log log = LogFactory.getLog(Receiver.class);

    @RabbitListener(queues = "spring-boot")
    public void processOrder(String data) {
        log.error("processing: " + data);
    }

}
