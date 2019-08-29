package com.netty.demo.core;

import com.netty.demo.server.protocol.packet.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangyi
 * @description: 退出群聊响应处理器
 * @date 2019/8/28 17:10
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.println("退出群[" + responsePacket.getGroupId() + "]成功!");
        } else {
            System.err.println("退出群[" + responsePacket.getGroupId() + "]失败，原因为：" + responsePacket.getReason());
        }
    }
}
