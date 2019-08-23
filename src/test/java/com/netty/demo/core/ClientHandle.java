package com.netty.demo.core;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.coder.PacketDecode;
import com.netty.demo.server.protocol.coder.PacketEncode;
import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import com.netty.demo.server.protocol.packet.LoginResponsePacket;
import com.netty.demo.server.protocol.packet.MessageResponsePacket;
import com.netty.demo.server.protocol.packet.Packet;
import com.netty.demo.server.protocol.serialize.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/20 19:29
 */
public class ClientHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送登录请求
        LoginRequestPacket requestPacket = LoginRequestPacket.of("admin", "123456");
        System.out.println("开始登录，requestPacket" + requestPacket);
        // 转码数据包
        ByteBuf requestBuf = PacketEncode.encode(ctx.alloc(), requestPacket, SerializerAlgorithm.JSON);
        // 发送数据
        ctx.channel().writeAndFlush(requestBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        // 解码数据
        Packet responsePacket = PacketDecode.decode(byteBuf);
        if (responsePacket instanceof LoginResponsePacket) {
            System.out.println("登录结果：" + responsePacket);
            if (((LoginResponsePacket) responsePacket).isSuccess()) {
                ctx.channel().attr(ProtocolConstant.LOGIN).set(true);
            } else {
                ctx.channel().attr(ProtocolConstant.LOGIN).set(false);
            }
        } else if (responsePacket instanceof MessageResponsePacket) {
            System.out.println("客户端收到服务端发送的消息：" + responsePacket);
        } else {
            System.out.println("invalid response packet");
        }
    }
}
