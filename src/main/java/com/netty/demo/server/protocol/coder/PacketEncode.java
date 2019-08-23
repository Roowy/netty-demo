package com.netty.demo.server.protocol.coder;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.packet.Packet;
import com.netty.demo.server.protocol.serialize.Serializer;
import com.netty.demo.server.protocol.serialize.SerializerAlgorithm;
import com.netty.demo.server.protocol.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author wangyi
 * @description: 数据包转码
 * @date 2019/8/20 16:32
 */
public class PacketEncode {

    /**
     * 将数据包转码成ByteBuf格式，使用默认序列化协议,默认分配器
     *
     * @param packet
     * @return
     */
    public static ByteBuf encode(Packet packet) {
        return encode(packet, SerializerAlgorithm.JSON);
    }

    /**
     * 将数据包转码成ByteBuf格式，使用默认分配器
     *
     * @param packet
     * @param algorithm
     * @return
     */
    public static ByteBuf encode(Packet packet, byte algorithm) {
        return encode(ByteBufAllocator.DEFAULT, packet, algorithm);
    }

    /**
     * 将数据包转码成ByteBuf格式
     * 1.开始标记--4个字节
     * 2.版本号--1个字节
     * 3.序列化算法--1个字节
     * 4.消息指令--1个字节
     * 5.数据包字节长度--4个字节
     * 6.数据--可变字节
     * 7.结束标记--4个字节
     *
     * @param byteBufAllocator
     * @param packet
     * @param algorithm
     * @return
     */
    public static ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet, byte algorithm) {
        // 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        return encode(byteBuf, packet, algorithm);
    }

    /**
     * 将数据包转码成ByteBuf格式
     * 1.开始标记--4个字节
     * 2.版本号--1个字节
     * 3.序列化算法--1个字节
     * 4.消息指令--1个字节
     * 5.整个tcp数据包字节长度--4个字节（数据长度+15）
     * 6.数据--可变字节
     * 7.结束标记--4个字节
     *
     * @param byteBuf
     * @param packet
     * @param algorithm
     * @return
     */
    public static ByteBuf encode(ByteBuf byteBuf, Packet packet, byte algorithm) {
        Serializer serializer = SerializerFactory.getSerializer(algorithm);
        // 序列化数据包
        byte[] data = serializer.serialize(packet);
        byteBuf.writeInt(ProtocolConstant.START_FLAG);
        byteBuf.writeByte(ProtocolConstant.VERSION);
        byteBuf.writeByte(algorithm);
        byteBuf.writeByte(packet.getCommand());
        // tcp包长度
        byteBuf.writeInt(data.length + 15);
        byteBuf.writeBytes(data);
        byteBuf.writeInt(ProtocolConstant.END_FLAG);
        return byteBuf;
    }

    /**
     * 转码
     *
     * @param byteBuf
     * @param packet
     * @return
     */
    public static ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        return encode(byteBuf, packet, SerializerAlgorithm.JSON);
    }
}
