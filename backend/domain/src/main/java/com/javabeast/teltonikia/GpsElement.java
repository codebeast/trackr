package com.javabeast.teltonikia;

import com.javabeast.geocode.GeocodedLocation;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.DecimalFormat;

@Data
@Builder
@Document
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GpsElement implements Serializable {
    private static final long serialVersionUID = -4557304960075040713L;
    private static final double EPSILON = 0.0001;

    private double latitude;
    private double longitude;
    private long altitude;
    private long angle;
    private int satellites;
    private long speed;

    @DBRef
    private GeocodedLocation geocodedLocation;


    @Override
    public int hashCode() {
        double hash = 17;
        hash = hash + 31 * (double) Math.round(latitude * 100000d) / 100000d;
        hash = hash + 31 * (double) Math.round(longitude * 100000d) / 100000d;
        return (int) hash;
    }

    @Override
    public boolean equals(Object obj) {
        final GpsElement gpsElement = (GpsElement) obj;
        boolean lat = (Math.abs(this.latitude - gpsElement.latitude) < EPSILON);
        boolean lng = (Math.abs(this.longitude - gpsElement.longitude) < EPSILON);
        return lat && lng;
    }
}
