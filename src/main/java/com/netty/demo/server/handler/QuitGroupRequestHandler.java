package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.QuitGroupRequestPacket;
import com.netty.demo.server.protocol.packet.QuitGroupResponsePacket;
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
 * @description: 退出群聊请求处理器
 * @date 2019/8/28 17:12
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception {
        String groupId = requestPacket.getGroupId();
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        // 1.获取群对应的channelGroup
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (Objects.nonNull(channelGroup)) {
            // 2.移除当前用户的channel
            boolean result = channelGroup.remove(ctx.channel());
            if (result) {
                responsePacket.setSuccess(true);
                responsePacket.setReason(String.format("退出群聊成功，groupId=%s,userId=%s", groupId, SessionUtil.getSession(ctx.channel()).getUserId()));
                log.info("退出群聊成功，groupId={},userId={}", groupId, SessionUtil.getSession(ctx.channel()).getUserId());
                // 如果群中只剩一个用户，则删除群信息
                if (channelGroup.size() <= 1) {
                    channelGroup.clear();
                    SessionUtil.unBindChannelGroup(groupId);
                    log.info("群用户少于等于1人，删除群，groupId={}", groupId);
                }
            } else {
                responsePacket.setReason(String.format("退出群聊失败，用户不在群聊中，groupId=%s,userId=%s", groupId, SessionUtil.getSession(ctx.channel()).getUserId()));
                responsePacket.setSuccess(false);
            }
        } else {
            responsePacket.setReason(String.format("退出群聊失败，群聊不存在，groupId=%s", groupId));
            responsePacket.setSuccess(false);
        }
        ctx.channel().writeAndFlush(responsePacket);
    }
}
