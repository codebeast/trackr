package com.javabeast.processors;

import com.javabeast.TrackerMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by jeffreya on 06/11/2016.
 */

@Service
public class IOEvents {

    @Value("${trackr.ioevents.queue}")
    private String ioEventsQueue;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public IOEvents(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addToQueue(final TrackerMessage message) {
        System.out.println("IOEvents.addToQueue");
        rabbitTemplate.convertAndSend("ioevents", message);
    }



}