package com.netty.demo.server;

import com.netty.demo.server.protocol.coder.PacketDecode;
import com.netty.demo.server.protocol.coder.PacketEncode;
import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import com.netty.demo.server.protocol.packet.Packet;
import com.netty.demo.server.protocol.serialize.JSONSerializer;
import com.netty.demo.server.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/20 17:37
 */
public class TestCoder {
    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketEncode packetCodeC = new PacketEncode();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);

        PacketDecode packetDecode = new PacketDecode();
        Packet decodedPacket = packetDecode.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
