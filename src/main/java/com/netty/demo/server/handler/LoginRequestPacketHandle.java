package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import com.netty.demo.server.protocol.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangyi
 * @description: 登录数据包处理器
 * @date 2019/8/21 15:02
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestPacketHandle extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(doLogin(ctx, msg));
    }

    private LoginResponsePacket doLogin(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        log.info("客户端发送登录信息：【{}】", requestPacket);
        boolean loginResult = Objects.equals("admin", requestPacket.getUsername()) && Objects.equals("123456", requestPacket.getPassword());
        if (loginResult) {
            ctx.channel().attr(ProtocolConstant.LOGIN).set(true);
            return LoginResponsePacket.of(true, "登录成功！");
        } else {
            return LoginResponsePacket.of(false, "登录失败，账号或密码错误！");
        }
    }
}
