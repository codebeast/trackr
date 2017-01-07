package com.javabeast.processors;

import com.javabeast.TrackerMessage;
import com.javabeast.domain.gecode.Location;
import com.javabeast.domain.gecode.MapQuestGeocodeResult;
import com.javabeast.geocode.GeocodedLocation;
import com.javabeast.repo.GeocodedLocationRepo;
import com.javabeast.repo.TrackerMessageRepo;
import com.javabeast.teltonikia.GpsElement;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.NotSerializableException;


@Log4j
@Service
@CacheConfig(cacheNames = "geocoder")
public class Geocoder {


    @Value("${trackr.reversegeocode.queue}")
    private String reverseGeocoderQueue;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    private final GeocodedLocationRepo geocodedLocationRepo;

    private final TrackerMessageRepo trackerMessageRepo;

    @Autowired
    public Geocoder(final RabbitTemplate rabbitTemplate, final RestTemplate restTemplate,
                    final GeocodedLocationRepo geocodedLocationRepo, final TrackerMessageRepo trackerMessageRepo) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
        this.geocodedLocationRepo = geocodedLocationRepo;
        this.trackerMessageRepo = trackerMessageRepo;
    }

    public void addToQueue(final TrackerMessage message) {
        rabbitTemplate.convertAndSend("reversegeocode", message);
    }

    public boolean reverseGeocode(final TrackerMessage trackerMessage) {
        System.out.println("Geocoder.reverseGeocode");
        final GpsElement gpsElement = trackerMessage.getGpsElement();

        try {
            final GeocodedLocation geocodedLocation = getGeocodedLocation(gpsElement);
            gpsElement.setGeocodedLocation(geocodedLocation);
            trackerMessageRepo.save(trackerMessage);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Cacheable("geocodedLocations")
    private GeocodedLocation getGeocodedLocation(final GpsElement gpsElement) throws IOException {
        try {
            System.out.println("Geocoder.getGeocodedLocation");
            System.out.println("sleeping, should have cached it mate");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final double latitude = gpsElement.getLatitude();
        final double longitude = gpsElement.getLongitude();
        final MapQuestGeocodeResult mapQuestGeocodeResult = restTemplate.getForObject("https://www.mapquestapi.com/geocoding/v1/reverse?key=aGuTKpOScwKlumRI93qMRheuNqdyuIEl&location=" + latitude + "," + longitude + "&outFormat=json&thumbMaps=false", MapQuestGeocodeResult.class);
        final GeocodedLocation geocodedLocation = convertFromMapQuest(mapQuestGeocodeResult);
        geocodedLocationRepo.save(geocodedLocation);
        return geocodedLocation;
    }

    private GeocodedLocation convertFromMapQuest(final MapQuestGeocodeResult mapQuestGeocodeResult) {
        final Location location = mapQuestGeocodeResult.getResults().get(0).getLocations().get(0);
        return GeocodedLocation.builder()
                .adminArea1(location.getAdminArea1())
                .adminArea2(location.getAdminArea2())
                .adminArea3(location.getAdminArea3())
                .adminArea4(location.getAdminArea4())
                .adminArea5(location.getAdminArea5())
                .street(location.getStreet())
                .postalCode(location.getPostalCode())
                .build();
    }

}