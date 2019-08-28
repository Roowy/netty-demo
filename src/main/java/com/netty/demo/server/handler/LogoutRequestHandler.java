package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.LogoutRequestPacket;
import com.netty.demo.server.protocol.packet.LogoutResponsePacket;
import com.netty.demo.server.session.Session;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangyi
 * @description: 登录请求包处理器
 * @date 2019/8/28 11:46
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        // 解绑
        Session session = SessionUtil.getSession(ctx.channel());
        SessionUtil.unBindSession(ctx.channel());
        log.info("用户：{},退出登录", session.getUserName());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        // 发送响应包
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
