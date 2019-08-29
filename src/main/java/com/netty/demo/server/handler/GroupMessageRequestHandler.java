package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.GroupMessageRequestPacket;
import com.netty.demo.server.protocol.packet.GroupMessageResponsePacket;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangyi
 * @description: 群消息请求包
 * @date 2019/8/28 18:52
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String groupId = msg.getToGroupId();
        // 1.获取群对应的GroupChannel
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (Objects.nonNull(channelGroup)) {
            GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            responsePacket.setFromGroupId(groupId);
            responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));
            responsePacket.setMessage(msg.getMessage());
            channelGroup.writeAndFlush(responsePacket);
        } else {
            log.info("发送群消息失败，群不存在，groupId={}", groupId);
        }
    }
}
