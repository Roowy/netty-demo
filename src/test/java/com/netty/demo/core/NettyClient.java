package com.netty.demo.core;

import com.netty.demo.server.handler.PacketDecodeHandle;
import com.netty.demo.server.handler.PacketEncodeHandle;
import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.protocol.coder.PacketEncode;
import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import com.netty.demo.server.protocol.packet.MessageRequestPacket;
import com.netty.demo.server.protocol.serialize.SerializerAlgorithm;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
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
        LoginRequestPacket loginRequestPacket = LoginRequestPacket.of("admin", "123456");
        channel.writeAndFlush(PacketEncode.encode(channel.alloc(), loginRequestPacket, SerializerAlgorithm.JSON));
        new Thread(() -> {
            while (!Thread.interrupted()) {
//                if (channel.attr(ProtocolConstant.LOGIN).get() != null && Objects.equals(Boolean.TRUE, channel.attr(ProtocolConstant.LOGIN).get())) {
                System.out.println("输入消息发送至服务端: ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                MessageRequestPacket packet = new MessageRequestPacket();
                packet.setMessage(line);
                ByteBuf byteBuf = PacketEncode.encode(channel.alloc(), packet, SerializerAlgorithm.JSON);
                channel.writeAndFlush(byteBuf);
//                }
            }
        }).start();
    }
}
