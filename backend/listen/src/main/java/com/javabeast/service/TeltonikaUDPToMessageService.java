package com.javabeast.service;

import com.javabeast.teltonikia.AVLData;
import com.javabeast.teltonikia.AVLPacketHeader;
import com.javabeast.teltonikia.TeltonikaMessage;
import com.javabeast.teltonikia.UDPChannelHeader;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
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

        int offset = 0;

        for (int i = 0; i < 1; i++) {
            final int start = offset;

            int position = offset;
            final String timestampHex = hexData.substring(position, position += 16);
            final int priority = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
            final String gpsHex = hexData.substring(position, position += 30);
            final String ioHex = hexData.substring(position);
            //System.out.println(ioHex);

            final int eventId = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
            final int ioEventsTotal = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
            final int oneByteEventTotal = Integer.parseInt(hexData.substring(position, position += 2), RADIX);

            for (int oneByteEventCount = 0; oneByteEventCount < oneByteEventTotal; oneByteEventCount++) {
                final int elementId = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
                final int elementValue = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
                System.out.println("1byte");
                System.out.println(elementId + ":" + elementValue);
            }

            final int twoByteEventTotal = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
            for (int twoByteEventCount = 0; twoByteEventCount < twoByteEventTotal; twoByteEventCount++) {
                final int elementId = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
                final int elementValue = Integer.parseInt(hexData.substring(position, position += 4), RADIX);
                System.out.println("2byte");
                System.out.println(elementId + ":" + elementValue);
            }

            final int fourByteEventTotal = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
            for (int fourByteEventCount = 0; fourByteEventCount < fourByteEventTotal; fourByteEventCount++) {
                final int elementId = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
                final int elementValue = Integer.parseInt(hexData.substring(position, position += 8), RADIX);
                System.out.println("4byte");
                System.out.println(elementId + ":" + elementValue);
            }

            final int eightByteEventTotal = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
            for (int eightByteEventCount = 0; eightByteEventCount < eightByteEventTotal; eightByteEventCount++) {
                final int elementId = Integer.parseInt(hexData.substring(position, position += 2), RADIX);
                final int elementValue = Integer.parseInt(hexData.substring(position, position += 16), RADIX);
                System.out.println("8byte");
                System.out.println(elementId + ":" + elementValue);
            }


            offset += (position - start);
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
