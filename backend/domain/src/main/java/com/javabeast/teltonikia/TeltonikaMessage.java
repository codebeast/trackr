package com.javabeast.teltonikia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class TeltonikaMessage implements Serializable {

    private static final long serialVersionUID = -4557304960075040713L;

    @Id
    private ObjectId id;

    private UDPChannelHeader udpChannelHeader;
    private AVLPacketHeader avlPacketHeader;
    private List<AVLData> avlData;

}
