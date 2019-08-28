package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.MessageRequestPacket;
import com.netty.demo.server.protocol.packet.MessageResponsePacket;
import com.netty.demo.server.session.Session;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.Channel;
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
        doMessage(ctx, msg);
    }

    /**
     * 发送消息
     *
     * @param ctx
     * @param messageRequestPacket
     * @return
     */
    private void doMessage(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4.将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.error("{}不在线，发送失败!", messageRequestPacket.getToUserId());
        }
    }
}
