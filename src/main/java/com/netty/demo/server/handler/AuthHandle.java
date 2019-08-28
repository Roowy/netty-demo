package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/22 9:39
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class AuthHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            log.error("无登录验证，强制关闭连接");
            ctx.channel().close();
        } else {
            // 已登录，移除处理器
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
