package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.MessageRequestPacket;
import com.netty.demo.server.protocol.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangyi
 * @description: 消息数据处理器
 * @date 2019/8/21 15:20
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class MessageRequestPacketHandle extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(doMessage(ctx, msg));
    }

    /**
     * 处理消息
     *
     * @param ctx
     * @param packet
     * @return
     */
    private MessageResponsePacket doMessage(ChannelHandlerContext ctx, MessageRequestPacket packet) {
        log.info("服务端收到客户端发送的消息：【{}】", packet.getMessage());
        return MessageResponsePacket.of("我是服务端，我已收到客户端发送的消息：" + packet.getMessage());
    }
}
