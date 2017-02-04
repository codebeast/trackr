package com.javabeast.service;

import com.javabeast.tracker.AVLData;
import com.javabeast.tracker.UDPMessage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeltonicaListenerTest {

    private static final int RADIX = 16;
    private static final String MESSAGE_FILE_PATH = "/Users/jeffreya/workspace/trackr/backend/listen/src/test/resources/tracker_message.dat";
    private static String hexString;

    @BeforeClass
    public static void init() {
        try {
            final Path path = Paths.get(MESSAGE_FILE_PATH);
            final byte[] trackerData = Files.readAllBytes(path);
            hexString = ByteArrayToString(trackerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadedFileCorrectly() {
        final int expectedByteCount = 626 * 2;
        assertEquals("Should load the right number of bytes", expectedByteCount, hexString.length());
    }

    @Test
    public void getUDPMessageHeader() {
        final Integer expectedPacketLength = 624;
        final Integer expectedPacketId = 51966;
        final Integer expectedPacketType = 1;

        final UDPMessage udpMessage = getUDPMessage(hexString);
        assertEquals("should show the right message size", expectedPacketLength, udpMessage.getPacketLength());
        assertEquals("should show the right message id ", expectedPacketId, udpMessage.getPacketId());
        assertEquals("should show the right message type", expectedPacketType, udpMessage.getPacketType());


    }


    private int getLength(String hexString) {
        final String lengthHex = hexString.substring(0, 4);
        return Integer.parseInt(lengthHex, RADIX);
    }

    private Integer getUDPChannelHeader(final String hexString) {
        return Integer.parseInt(hexString.substring(4, 8), RADIX);
    }

    private int getPacketType(final String hexString) {
        final String packetType = hexString.substring(8, 10);
        return Integer.parseInt(packetType, RADIX);
    }

    private UDPMessage getUDPMessage(final String hex) {
        final Integer messageSize = getLength(hex);
        final Integer packetId = getUDPChannelHeader(hex);
        final Integer packetType = getPacketType(hex);


        return UDPMessage.builder()
                .packetLength(messageSize)
                .packetId(packetId)
                .packetType(packetType)
                .build();
    }

    private List<AVLData> getAVLData(final ByteBuffer avlData) {
        final int position = avlData.position();
        final byte codecId = avlData.get();
        final byte numberOfData = avlData.get();
        ByteArrayToString(avlData.array());

        return null;
    }


    private static String ByteArrayToString(byte[] ba) {
        final StringBuilder sb = new StringBuilder();
        for (byte b : ba) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private static ByteBuffer fromByteArray(final byte[] bytes) {
        final ByteBuffer ret = ByteBuffer.wrap(new byte[bytes.length]);
        ret.put(bytes);
        ret.flip();
        return ret;
    }


}
