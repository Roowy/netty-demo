package com.netty.demo.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/20 10:28
 */
@Component
@Slf4j
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端读数据
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("服务端读取到数据->{}", byteBuf.toString(Charset.forName("utf-8")));
        // 收到数据后，发送一条数据给客户端
        ByteBuf ackBuffer = ctx.alloc().buffer();
        ackBuffer.writeBytes("您好！我是服务端，已收到你的数据".getBytes(Charset.forName("utf-8")));
        ctx.channel().writeAndFlush(ackBuffer);
    }
}
