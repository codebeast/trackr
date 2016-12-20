package com.javabeast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackerPreParsedMessage implements Serializable{

    private static final long serialVersionUID = -4557304960075040713L;

    @Id
    private ObjectId id;

    private String uuid;
    private String message;
    private Date timestamp;

    @DBRef
    private List<Person> people;
}