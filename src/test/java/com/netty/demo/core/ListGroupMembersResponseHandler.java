package com.netty.demo.core;

import com.netty.demo.server.protocol.packet.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 18:00
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.println("群[" + responsePacket.getGroupId() + "]中的人包括：" + responsePacket.getSessionList());
        } else {
            System.err.println("获取群[" + responsePacket.getGroupId() + "]用户列表失败，原因为：" + responsePacket.getReason());
        }
    }
}
