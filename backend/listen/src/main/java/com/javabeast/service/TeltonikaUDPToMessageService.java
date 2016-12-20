package com.javabeast.service;

import com.javabeast.teltonikia.AVLData;
import com.javabeast.teltonikia.AVLPacketHeader;
import com.javabeast.teltonikia.TeltonikaMessage;
import com.javabeast.teltonikia.UDPChannelHeader;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;


@Service
public class TeltonikaUDPToMessageService {

    private static final int RADIX = 16;
    private static final int AVL_PACKET_HEADER_OFFSET = 10;
    private static final int AVL_PACKET_HEADER_SIZE = 34;
    private static final int AVL_DATA_OFFSET = AVL_PACKET_HEADER_OFFSET + AVL_PACKET_HEADER_SIZE;

    public TeltonikaMessage convertUDPToMessage(final byte[] bytes) {
        final String hexData = getHexData(bytes);
        final UDPChannelHeader udpChannelHeader = parseUDPChannelHeader(hexData);
        final AVLPacketHeader avlPacketHeader = parseAVLPacketHeader(hexData);
        final List<AVLData> avlDataList = parseAVLData(hexData);

        return TeltonikaMessage.builder()
                .udpChannelHeader(udpChannelHeader)
                .avlPacketHeader(avlPacketHeader)
                .avlData(avlDataList)
                .build();
    }

    private UDPChannelHeader parseUDPChannelHeader(final String hexData) {
        final int length = getLength(hexData);
        final String id = getUDPChannelHeader(hexData);
        final int packetType = getPacketType(hexData);
        return UDPChannelHeader.builder()
                .length(length)
                .id(id)
                .packetType(packetType)
                .build();
    }

    private int getLength(String hexString) {
        final String lengthHex = hexString.substring(0, 4);
        return Integer.parseInt(lengthHex, RADIX);
    }

    private String getUDPChannelHeader(final String hexString) {
        return hexString.substring(4, 8);
    }

    private int getPacketType(final String hexString) {
        final String packetType = hexString.substring(8, 10);
        return Integer.parseInt(packetType, RADIX);
    }

    private AVLPacketHeader parseAVLPacketHeader(final String data) {
        final String hexData = data.substring(AVL_PACKET_HEADER_OFFSET);
        final int avlPacketHeaderId = getAVLPacketHeaderId(hexData);
        final String imei = getImeiNumber(hexData);

        return AVLPacketHeader.builder()
                .id(avlPacketHeaderId)
                .imei(imei)
                .build();
    }

    private int getAVLPacketHeaderId(final String hexData) {
        final String idHex = hexData.substring(0, 2);
        return Integer.parseInt(idHex, RADIX);
    }

    private String getImeiNumber(final String hexData) {
        final String imeiHex = hexData.substring(2, 34);
        final byte[] bytes = DatatypeConverter.parseHexBinary(imeiHex);
        return new String(bytes);
    }

    private List<AVLData> parseAVLData(final String data) {
        final String hexData = data.substring(AVL_DATA_OFFSET);
        final int numOfRecords = getNumberOfRecords(hexData);
        return getAvlDatas(numOfRecords, hexData);
    }

    private List<AVLData> getAvlDatas(final int numOfRecords, final String data) {
        final String hexData = data.substring(4);
        final List<AVLData> avlDataList = new ArrayList<>();
        for (int i = 0; i < numOfRecords; i++) {
/*

            00000113fc208dff – Timestamp in milliseconds (1185345998335 → 1185345998,335 in Unix Timestamp = 25 Jul 2007 06:46:38 UTC)
            00 – Priority
            GPS Element
            0f14f650 – Longitude 253032016 = 25,3032016º N
            209cca80 – Latitude 547146368 = 54,7146368 º E
            006f – Altitude 111 meters
            00d6 – Angle 214º
            04 – 4 Visible sattelites
            0004 – 4 km/h speed
            IO Element
            00 – IO element ID of Event generated (in this case when 00 – data generated not on event)
                04 – 4 IO elements in record
                03 – 3 IO elements, which length is 1 Byte
                01 – IO element ID = 01
                01 – 1’st IO element’s value = 1
                15 – IO element ID = 21
                03 – 21’st IO element’s value = 3
                16 – IO element ID = 22
                03 – 22’nd IO element’s value = 3
                00 – 0 IO elements, which value length is 2 Bytes
                01 – 1 IO element, which value length is 4 Bytes
                46 – IO element ID = 70
                5
                0000015d – 70’th IO element’s value = 349
                00 – 0 IO elements, which value length is 8 Bytes
*/

            final String timestampHex = hexData.substring(0, 16);
            final int priority = Integer.parseInt(hexData.substring(16, 18), RADIX);
            final String gpsHex = hexData.substring(18, 48);
            final String ioHex = hexData.substring(48, 100);
            System.out.println(ioHex);


            final int eventId = Integer.parseInt(ioHex.substring(0, 2));
            final int ioEventsTotal = Integer.parseInt(ioHex.substring(2, 4));
            final int oneByteEventTotal = Integer.parseInt(ioHex.substring(4, 6));
            for (int oneByteEventCount = 0; oneByteEventCount < oneByteEventTotal; oneByteEventCount++) {
                final int elementId = Integer.parseInt(ioHex.substring(6, 8), RADIX);
                System.out.println(elementId);
            }


//            Timestamp 8
//            Priority 1
//            GPS Element 15
//            IO Element ?


            final AVLData avlData = AVLData.builder()
                    .build();
            avlDataList.add(avlData);
        }
        return avlDataList;
    }

    private int getNumberOfRecords(String hexData) {
        return Integer.parseInt(hexData.substring(2, 4), RADIX);
    }

    private int getCodec(final String codecHex) {
        return Integer.parseInt(codecHex.substring(0, 2), RADIX);
    }

    private String getHexData(final byte[] bytes) {
        return new String(bytes).replace("\n", "").replace("\r", "");
    }
}
