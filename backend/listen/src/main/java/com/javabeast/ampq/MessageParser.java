package com.javabeast.ampq;

import com.javabeast.TrackerPreParsedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by jeffreya on 07/11/2016.
 * Message Parser
 */

@Service
public class MessageParser {


    @Value("${trackr.unprocessed.queue}")
    private String unprocessedQueue;

    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public MessageParser(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void parseMessage(final byte[] bytes) {
        final TrackerPreParsedMessage build = TrackerPreParsedMessage.builder()
                .timestamp(new Date())
                .message("hello world").build();
        rabbitTemplate.convertAndSend(unprocessedQueue, build);
    }

}
