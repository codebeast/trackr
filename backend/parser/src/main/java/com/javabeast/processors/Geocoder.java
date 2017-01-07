package com.javabeast.processors;

import com.javabeast.TrackerMessage;
import com.javabeast.geocode.GeocodedLocation;
import com.javabeast.repo.TrackerMessageRepo;
import com.javabeast.services.GeocodeService;
import com.javabeast.teltonikia.GpsElement;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Log4j
@Service
public class Geocoder {


    @Value("${trackr.reversegeocode.queue}")
    private String reverseGeocoderQueue;

    private final RabbitTemplate rabbitTemplate;

    private final TrackerMessageRepo trackerMessageRepo;

    private final GeocodeService geocodeService;

    @Autowired
    public Geocoder(final RabbitTemplate rabbitTemplate, final TrackerMessageRepo trackerMessageRepo, final GeocodeService geocodeService) {
        this.rabbitTemplate = rabbitTemplate;
        this.trackerMessageRepo = trackerMessageRepo;
        this.geocodeService = geocodeService;
    }

    public void addToQueue(final TrackerMessage message) {
        rabbitTemplate.convertAndSend("reversegeocode", message);
    }

    public boolean reverseGeocode(final TrackerMessage trackerMessage) {
        System.out.println("Geocoder.reverseGeocode");
        final GpsElement gpsElement = trackerMessage.getGpsElement();

        try {

            long start = System.currentTimeMillis();


            final GeocodedLocation geocodedLocation = geocodeService.getGeocodedLocation(gpsElement);
            gpsElement.setGeocodedLocation(geocodedLocation);
            trackerMessageRepo.save(trackerMessage);
            long end = System.currentTimeMillis();
            System.out.println("took:" + (end - start));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}