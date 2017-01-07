package com.javabeast.services;

import com.javabeast.domain.gecode.Location;
import com.javabeast.domain.gecode.MapQuestGeocodeResult;
import com.javabeast.geocode.GeocodedLocation;
import com.javabeast.repo.GeocodedLocationRepo;
import com.javabeast.teltonikia.GpsElement;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GeocodeService {

    private final RestTemplate restTemplate;

    private final GeocodedLocationRepo geocodedLocationRepo;

    public GeocodeService(final RestTemplate restTemplate, final GeocodedLocationRepo geocodedLocationRepo) {
        this.restTemplate = restTemplate;
        this.geocodedLocationRepo = geocodedLocationRepo;
    }

    @Cacheable
    public GeocodedLocation getGeocodedLocation(final GpsElement gpsElement) throws IOException {
        try {
            System.out.println("cache miss!");
            Thread.sleep(3000);
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
