package com.javabeast.teltonikia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class AVLData implements Serializable {
    private static final long serialVersionUID = -4557304960075040713L;

    private ObjectId id;

    private Date date;
    private int priority;
    private String GPS;
    private String ios;
}
