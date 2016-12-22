package com.javabeast.processors;

import com.javabeast.TrackerMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by jeffreya on 06/11/2016.
 */

@Service
public class Geocoder {

    //https://www.mapquestapi.com/geocoding/v1/reverse?key=aGuTKpOScwKlumRI93qMRheuNqdyuIEl&location=53.595367%2C-2.235829&outFormat=json&thumbMaps=false

    @Value("${trackr.reversegeocode.queue}")
    private String reverseGeocoderQueue;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Geocoder(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addToQueue(final TrackerMessage message) {
        System.out.println("Geocoder.addToQueue");
        rabbitTemplate.convertAndSend("reversegeocode", message);
    }



}