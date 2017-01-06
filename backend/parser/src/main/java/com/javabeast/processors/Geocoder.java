package com.javabeast.processors;

import com.javabeast.TrackerMessage;
import com.javabeast.teltonikia.GpsElement;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Created by jeffreya on 06/11/2016.
 */

@Log4j
@Service
public class Geocoder {


    @Value("${trackr.reversegeocode.queue}")
    private String reverseGeocoderQueue;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Autowired
    public Geocoder(final RabbitTemplate rabbitTemplate, final RestTemplate restTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
    }

    public void addToQueue(final TrackerMessage message) {
        System.out.println("Geocoder.addToQueue");
        log.warn(message);
        reverseGeocode(message.getGpsElement());
        rabbitTemplate.convertAndSend("reversegeocode", message);
    }


    public void reverseGeocode(final GpsElement gpsElement) {

        final double latitude = gpsElement.getLatitude();
        final double longitude = gpsElement.getLongitude();

        //https://www.mapquestapi.com/geocoding/v1/reverse?key=aGuTKpOScwKlumRI93qMRheuNqdyuIEl&location=53.595367%2C-2.235829&outFormat=json&thumbMaps=false

        final String forObject = restTemplate.getForObject("https://www.mapquestapi.com/geocoding/v1/reverse?key=aGuTKpOScwKlumRI93qMRheuNqdyuIEl&location=53.595367,-2.235829&outFormat=json&thumbMaps=false", String.class);
        System.out.println("forObject:" + forObject);
    }


}