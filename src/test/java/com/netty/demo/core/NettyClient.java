package com.netty.demo.core;

import com.netty.demo.console.ConsoleCommandManager;
import com.netty.demo.console.LoginConsoleCommand;
import com.netty.demo.server.handler.PacketDecodeHandle;
import com.netty.demo.server.handler.PacketEncodeHandle;
import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.coder.PacketEncode;
import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import com.netty.demo.server.protocol.packet.MessageRequestPacket;
import com.netty.demo.server.protocol.serialize.SerializerAlgorithm;
import com.netty.demo.server.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/19 18:11
 */
public class NettyClient {
    private static final int MAX_RETRY = 10;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4, -11, 0));
                        ch.pipeline().addLast(new PacketDecodeHandle());
                        ch.pipeline().addLast(new PacketEncodeHandle());
                        ch.pipeline().addLast(new LoginResponsePacketHandle());
                        ch.pipeline().addLast(new MessageResponsePacketHandle());
                        ch.pipeline().addLast(new CreateGroupResponseHandle());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                    }
                });
        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
        startConsoleThread(channel);
    }

    /**
     * 控制台打印消息线程
     *
     * @param channel
     */
    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(channel);
                } else {
                    consoleCommandManager.exec(channel);
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
