package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.coder.PacketDecode;
import com.netty.demo.server.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangyi
 * @description: 解码处理
 * @date 2019/8/21 14:50
 */
@Slf4j
public class PacketDecodeHandle extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketDecode.decode(in));
    }
}
