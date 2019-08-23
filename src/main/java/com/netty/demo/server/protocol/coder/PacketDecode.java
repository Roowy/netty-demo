package com.netty.demo.server.protocol.coder;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.ProtocolData;
import com.netty.demo.server.protocol.packet.Packet;
import com.netty.demo.server.protocol.packet.PacketFactory;
import com.netty.demo.server.protocol.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author wangyi
 * @description: 数据包解码
 * @date 2019/8/20 16:32
 */
@Slf4j
public class PacketDecode {

    /**
     * 将byteBuf转换成packet对象
     * 1.开始标记--4个字节
     * 2.版本号--1个字节
     * 3.序列化算法--1个字节
     * 4.消息指令--1个字节
     * 5.整个tcp数据包字节长度--4个字节（数据长度+15）
     * 6.数据--可变字节
     * 7.结束标记--4个字节
     *
     * @param byteBuf
     * @return
     */
    public static Packet decode(ByteBuf byteBuf) {
        int packLen = byteBuf.readableBytes();
        //如果数据包的长度小于15 说明tcp发生了拆包或者包异常，当前不进行处理
        if (packLen < ProtocolConstant.MIN_PROTOCOL_LEN) {
            log.warn("decoder pack len less than min limit");
            return null;
        }
        // 校验开始标记
        int startFlag = byteBuf.readInt();
        if (!Objects.equals(startFlag, ProtocolConstant.START_FLAG)) {
            log.warn("decoder not equals startFlag");
            return null;
        }
        // 协议包对象
        ProtocolData data = new ProtocolData();
        data.setStartFlag(startFlag);
        data.setVersion(byteBuf.readByte());
        data.setAlgorithm(byteBuf.readByte());
        data.setCommand(byteBuf.readByte());
        data.setLen(byteBuf.readInt());
        byte[] dataBytes = new byte[data.getLen()-15];
        byteBuf.readBytes(dataBytes);
        data.setData(dataBytes);
        data.setEndFlag(byteBuf.readInt());
        // 反序列化
        return SerializerFactory.getSerializer(data.getAlgorithm()).deserialize(PacketFactory.getPacketClass(data.getCommand()), data.getData());
    }
}
