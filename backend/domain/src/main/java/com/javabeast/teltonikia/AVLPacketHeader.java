package com.javabeast.teltonikia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class AVLPacketHeader implements Serializable {
    private static final long serialVersionUID = -4557304960075040713L;

    private int id;
    private String imei;
}
