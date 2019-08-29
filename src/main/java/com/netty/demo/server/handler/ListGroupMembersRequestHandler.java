package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.ListGroupMembersRequestPacket;
import com.netty.demo.server.protocol.packet.ListGroupMembersResponsePacket;
import com.netty.demo.server.session.Session;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wangyi
 * @description: 查询群成员请求处理器
 * @date 2019/8/28 17:46
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        ListGroupMembersResponsePacket respnsePacket = new ListGroupMembersResponsePacket();
        String groupId = msg.getGroupId();
        // 1.获取群的groupChannel
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (Objects.nonNull(channelGroup)) {
            respnsePacket.setGroupId(groupId);
            respnsePacket.setSuccess(true);
            // 2.遍历群成员的channel，查询对应Session信息
            List<Session> sessionList = channelGroup.stream().map(channel -> SessionUtil.getSession(channel)).collect(Collectors.toList());
            respnsePacket.setSessionList(sessionList);
        } else {
            log.error("获取群用户列表失败，群不存在，groupId={}", groupId);
            respnsePacket.setGroupId(groupId);
            respnsePacket.setSuccess(false);
            respnsePacket.setReason(String.format("群不存在，groupId=%s", groupId));
        }
        ctx.channel().writeAndFlush(respnsePacket);
    }
}
