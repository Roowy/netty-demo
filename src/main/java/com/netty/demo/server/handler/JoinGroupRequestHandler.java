package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.JoinGroupRequestPacket;
import com.netty.demo.server.protocol.packet.JoinGroupResponsePacket;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangyi
 * @description: 加群处理器
 * @date 2019/8/28 16:02
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        // 1.获取群对应的GroupChannel，然后把用户的Channel加进去
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (Objects.nonNull(channelGroup)) {
            Channel channel = channelGroup.find(ctx.channel().id());
            if (Objects.nonNull(channel)) {
                log.info("加群失败，已经是群成员，无须重复加入，groupId={}", groupId);
                responsePacket.setGroupId(groupId);
                responsePacket.setSuccess(false);
                responsePacket.setReason("加群失败，已经是群成员，无须重复加入，groupId=" + groupId);
            } else {
                channelGroup.add(ctx.channel());
                log.info("加群成功，groupId={}，userId={}", groupId, SessionUtil.getSession(ctx.channel()).getUserId());
                responsePacket.setGroupId(groupId);
                responsePacket.setSuccess(true);
                responsePacket.setReason("加群成功");
            }
        } else {
            log.info("加群失败，群不存在，groupId={}", groupId);
            responsePacket.setGroupId(groupId);
            responsePacket.setSuccess(false);
            responsePacket.setReason("群不存在，groupId=" + groupId);
        }
        ctx.channel().writeAndFlush(responsePacket);
    }
}
