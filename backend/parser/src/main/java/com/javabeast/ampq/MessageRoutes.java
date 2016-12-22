package com.javabeast.ampq;


import com.javabeast.TrackerMessage;
import com.javabeast.processors.Geocoder;
import com.javabeast.processors.MessageParser;
import com.javabeast.repo.PersonRepo;
import com.javabeast.repo.TrackerMessageRepo;
import com.javabeast.teltonikia.TeltonikaMessage;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by jeffreya on 05/11/2016.
 */

@Component
public class MessageRoutes {

    private Log log = LogFactory.getLog(MessageRoutes.class);

    @Autowired
    private Geocoder geocoder;

    @Autowired
    private MessageParser messageParser;

    @Autowired
    private TrackerMessageRepo trackerMessageRepo;


    @Autowired
    private PersonRepo personRepo;

    @RabbitListener(queues = "unprocessed")
    public void unprocessedQueue(TeltonikaMessage teltonikaMessage, Channel channel,
                                 @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("MessageRoutes.unprocessedQueue");
        messageParser.addToQueue(teltonikaMessage);
        channel.basicAck(tag, true);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "trackermessage", durable = "true"),
            exchange = @Exchange(value = "spring-boot-exchange", ignoreDeclarationExceptions = "true", type = "topic", durable = "true"),
            key = "orderRoutingKey"))
    public void trackerMessageQueue(TrackerMessage trackerMessage, Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("MessageRoutes.trackerMessageQueue");
        trackerMessageRepo.save(trackerMessage);

        //PUSH TO OTHER QUEUES
        channel.basicAck(tag, true);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "reversegeocode", durable = "true"),
            exchange = @Exchange(value = "spring-boot-exchange", ignoreDeclarationExceptions = "true", type = "topic", durable = "true"),
            key = "orderRoutingKey"))
    public void reverseGeocode(TrackerMessage message, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("MessageRoutes.reverseGeocode");
        channel.basicAck(tag, true);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "ioevents", durable = "true"),
            exchange = @Exchange(value = "spring-boot-exchange", ignoreDeclarationExceptions = "true", type = "topic", durable = "true"),
            key = "orderRoutingKey"))
    public void ioevents(TrackerMessage message, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("MessageRoutes.ioevents");
        channel.basicAck(tag, true);
    }


}
