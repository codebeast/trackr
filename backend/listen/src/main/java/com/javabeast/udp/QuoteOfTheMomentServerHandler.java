package com.javabeast.udp;

import com.javabeast.service.TeltonikaUDPToMessageService;
import com.javabeast.teltonikia.TeltonikaMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class QuoteOfTheMomentServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private final TeltonikaUDPToMessageService teltonikaUDPToMessageService = new TeltonikaUDPToMessageService();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        System.err.println(packet);
        ByteBuf buf = packet.content();
        byte[] bytes = new byte[buf.readableBytes()];
        int readerIndex = buf.readerIndex();
        buf.getBytes(readerIndex, bytes);

        final TeltonikaMessage teltonikaMessage = teltonikaUDPToMessageService.convertUDPToMessage(bytes);
        // System.out.println(teltonikaMessage);
        System.out.println(teltonikaMessage.getAvlData().size());
        System.out.println(teltonikaMessage);

        //Write ACK
        String ACK = "0005" + teltonikaMessage.getUdpChannelHeader().getId() + "01"
                + teltonikaMessage.getAvlPacketHeader().getId()
                + Integer.toHexString(0x100 | teltonikaMessage.getAvlData().size()).substring(1).toUpperCase();
        System.out.println(ACK);

        ctx.write(new DatagramPacket(Unpooled.copiedBuffer(hexStringToByteArray(ACK)), packet.sender()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();

    }

    private static byte[] hexStringToByteArray(String s) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(bos);

        for (int i = 0; i < s.length() / 2; i++) {
            int index = i * 2;
            int value = (Integer.parseInt(s.substring(index, index + 2), 16) & 0xff);
            dos.write(value);
        }
        dos.flush();
        return bos.toByteArray();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.

    }

}