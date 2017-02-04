package com.javabeast.tracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UDPMessage {
    private Integer packetLength;
    private Integer packetId;
    private Integer packetType;
    private List<AVLData> avlDataList;
}