package com.javabeast.teltonikia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class AVLData implements Serializable {
    private static final long serialVersionUID = -4557304960075040713L;

    @Id
    private ObjectId id;

    private Date timestamp;
    private int priority;
    private GpsElement gpsElement;
    private List<IOEvent> ioEventList;
}
