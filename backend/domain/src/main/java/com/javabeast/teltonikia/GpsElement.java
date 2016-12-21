package com.javabeast.teltonikia;

import lombok.*;
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

    private double latitude;
    private double longitude;
    private long altitude;
    private long angle;
    private int satellites;
    private long speed;
}
