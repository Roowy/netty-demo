package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.coder.PacketEncode;
import com.netty.demo.server.protocol.packet.Packet;
import com.netty.demo.server.protocol.serialize.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangyi
 * @description: 转码处理
 * @date 2019/8/21 14:55
 */
@Slf4j
public class PacketEncodeHandle extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketEncode.encode(out, msg, SerializerAlgorithm.JSON);
    }
}
