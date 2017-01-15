package com.javabeast.teltonikia;

import com.javabeast.geocode.GeocodedLocation;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

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

    @Transient
    public String getLatLngString() {
        return latitude + "," + longitude;
    }

    @DBRef
    private GeocodedLocation geocodedLocation;

    @Override
    public int hashCode() {
        final double lat = Math.round(latitude * 10000d) / 10000d;
        final double lng = Math.round(latitude * 10000d) / 10000d;
        return Long.valueOf(Double.doubleToLongBits(lat)).hashCode() +
                Long.valueOf(Double.doubleToLongBits(lng)).hashCode();

    }

}
