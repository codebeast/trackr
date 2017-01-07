package com.javabeast.geocode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeocodedLocation {

    @Id
    private ObjectId id;

    private String street;
    private String postalCode;
    private String adminArea5;
    private String adminArea4;
    private String adminArea3;
    private String adminArea2;
    private String adminArea1;
}
