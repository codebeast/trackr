package com.javabeast.service;

import com.javabeast.teltonikia.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TeltonikaUDPToMessageServiceTest {

    private final TeltonikaUDPToMessageService teltonikaUDPToMessageService = new TeltonikaUDPToMessageService();
    private final String dataString = "0178CAFE01DD31323334353637383930313233343536080400000113fc208dff000f14f650209cca80006f00d60400040004030101150316030001460000015d0000000113fc17610b000f14ffe0209cc580006e00c00500010004030101150316010001460000015e0000000113fc284945000f150f00209cd200009501080400000004030101150016030001460000015d0000000113fc267c5b000f150a50209cccc0009300680400000004030101150016030001460000015b0004";
    private final byte[] data = dataString.getBytes();

    @Test
    public void convertUDPToMessage() throws Exception {
        final TeltonikaMessage teltonikaMessage = teltonikaUDPToMessageService.convertUDPToMessage(data);
        assertNotNull(teltonikaMessage);
        assertNotNull(teltonikaMessage.getUdpChannelHeader());
        assertNotNull(teltonikaMessage.getAvlPacketHeader());
        assertNotNull(teltonikaMessage.getAvlData());
    }

    @Test
    public void parseUDPChannelHeader() throws Exception {
        final TeltonikaMessage teltonikaMessage = teltonikaUDPToMessageService.convertUDPToMessage(data);

        final UDPChannelHeader udpChannelHeader = teltonikaMessage.getUdpChannelHeader();
        assertNotNull(udpChannelHeader);

        final int length = udpChannelHeader.getLength();
        final int expectedLength = 376;
        assertEquals(expectedLength, length);

        final String id = udpChannelHeader.getId();
        final String expectedId = "CAFE";
        assertEquals(expectedId, id);

        final int packetType = udpChannelHeader.getPacketType();
        final int expectedPacketType = 1;
        assertEquals(expectedPacketType, packetType);
    }

    @Test
    public void parseAVLPacketHeader() {
        final TeltonikaMessage teltonikaMessage = teltonikaUDPToMessageService.convertUDPToMessage(data);
        final AVLPacketHeader avlPacketHeader = teltonikaMessage.getAvlPacketHeader();
        assertNotNull(avlPacketHeader);

        final int id = avlPacketHeader.getId();
        final int expectedId = 221;
        assertEquals(expectedId, id);

        final String imei = avlPacketHeader.getImei();
        final String expectedImei = "1234567890123456";
        assertEquals(expectedImei, imei);

    }

    @Test
    public void parseAVLData() {
        final TeltonikaMessage teltonikaMessage = teltonikaUDPToMessageService.convertUDPToMessage(data);
        final List<AVLData> avlDataList = teltonikaMessage.getAvlData();
        assertNotNull(avlDataList);

        final int expectedRecords = 4;
        assertEquals(expectedRecords, avlDataList.size());

        final AVLData avlData = avlDataList.get(0);
        assertNotNull(avlData);

        final GpsElement gpsElement = avlData.getGpsElement();
        assertNotNull(gpsElement);
    }


}