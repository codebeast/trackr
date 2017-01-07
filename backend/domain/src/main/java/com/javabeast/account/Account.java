package com.javabeast.account;

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
public class Account {

    @Id
    private ObjectId id;
    private String name;


}
