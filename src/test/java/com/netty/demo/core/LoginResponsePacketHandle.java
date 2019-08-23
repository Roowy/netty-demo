package com.netty.demo.core;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/21 15:42
 */
public class LoginResponsePacketHandle extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            System.out.println(new Date() + "客户端登录成功");
            ctx.channel().attr(ProtocolConstant.LOGIN).set(true);
        } else {
            System.out.println(new Date() + "客户端登录失败：" + packet);
            ctx.channel().attr(ProtocolConstant.LOGIN).set(false);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
