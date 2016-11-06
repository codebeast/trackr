package com.javabeast.ampq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by jeffreya on 05/11/2016.
 *
 */

@Component
public class Receiver {

    private Log log = LogFactory.getLog(Receiver.class);

    @RabbitListener(queues = "unprocessed")
    public void processOrder(String data, Channel channel,
                             @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        channel.basicAck(tag, true);
        log.error("processing: " + data);
    }

}
