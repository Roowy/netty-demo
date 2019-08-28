package com.netty.demo.core;

import com.netty.demo.server.protocol.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangyi
 * @description: 创建群聊通知包
 * @date 2019/8/28 11:41
 */
public class CreateGroupResponseHandle extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        System.out.println("群创建成功，群id 为" + msg.getGroupId() + " ,群用户：" + msg.getUserNameList());
    }
}
