package com.javabeast.ampq;


import com.javabeast.Person;
import com.javabeast.TrackerPreParsedMessage;
import com.javabeast.processors.Geocoder;
import com.javabeast.repo.PersonRepo;
import com.javabeast.repo.TrackerPreParsedMessageRepo;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


/**
 * Created by jeffreya on 05/11/2016.
 */

@Component
public class MessageRoutes {

    private Log log = LogFactory.getLog(MessageRoutes.class);

    @Autowired
    private Geocoder geocoder;

    @Autowired
    private TrackerPreParsedMessageRepo trackerPreParsedMessageRepo;


    @Autowired
    private PersonRepo personRepo;

    @RabbitListener(queues = "unprocessed")
    public void raw(TrackerPreParsedMessage message, Channel channel,
                    @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Unprocessed.raw");
        geocoder.pushMessage(message);
        final Person barry = Person.builder().name("barry").build();
        final Person save = personRepo.save(barry);
        message.setPeople(Collections.singletonList(save));
        trackerPreParsedMessageRepo.save(message);

        channel.basicAck(tag, true);

        final TrackerPreParsedMessage one = trackerPreParsedMessageRepo.findOne(message.getId().toString());
        for (Person p : one.getPeople()) {
            System.out.println("found");
            System.out.println(p.getName());
        }
    }

    @RabbitListener(queues = "reversegeocode")
    public void reverseGeocode(TrackerPreParsedMessage message, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Unprocessed.reverseGeocode");
        channel.basicAck(tag, true);
    }

    //@Cacheable("getResult")
    //public String getResult(final String input) {
    //return "hi";
    //}

}
