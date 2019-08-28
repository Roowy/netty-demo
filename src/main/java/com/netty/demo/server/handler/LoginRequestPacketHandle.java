package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import com.netty.demo.server.protocol.packet.LoginResponsePacket;
import com.netty.demo.server.session.Session;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

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

    /**
     * 连接关闭，解绑session
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private LoginResponsePacket doLogin(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(requestPacket.getVersion());
        responsePacket.setUserName(requestPacket.getUserName());
        if (valid(requestPacket)) {
            responsePacket.setSuccess(true);
            String userId = randomUserId();
            responsePacket.setUserId(userId);
            log.info("[{}]登录成功", requestPacket.getUserName());
            SessionUtil.bindSession(new Session(userId, requestPacket.getUserName()), ctx.channel());
        } else {
            responsePacket.setReason("账号密码校验失败");
            responsePacket.setSuccess(false);
            log.info("[{}]登录失败", requestPacket.getUserName());
        }
        return responsePacket;
    }


    /**
     * 随机生成用户id
     *
     * @return
     */
    private String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
