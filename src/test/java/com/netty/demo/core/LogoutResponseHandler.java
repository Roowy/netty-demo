package com.netty.demo.core;

import com.netty.demo.server.protocol.packet.LogoutResponsePacket;
import com.netty.demo.server.session.Session;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangyi
 * @description: 登录响应包处理器
 * @date 2019/8/28 11:44
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            Session session = SessionUtil.getSession(ctx.channel());
            System.out.println("已退出登录！");
            // 解绑
            SessionUtil.unBindSession(ctx.channel());
        } else {
            System.out.println("退出登录失败！");
        }
    }
}
