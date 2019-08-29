package com.netty.demo.core;

import com.netty.demo.server.protocol.packet.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangyi
 * @description: 加群响应处理器
 * @date 2019/8/28 16:21
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.println("加入群[" + responsePacket.getGroupId() + "]成功!");
        } else {
            System.err.println("加入群[" + responsePacket.getGroupId() + "]失败，原因为：" + responsePacket.getReason());
        }
    }
}
