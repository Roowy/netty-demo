package com.netty.demo.server.socket;

import com.netty.demo.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/19 17:58
 */
@Component
@Slf4j
public class NettyServerSocket {
    private final static int port = 8000;
    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workGroup = null;
    private ServerBootstrap serverBootstrap = null;
    @Autowired
    private LoginRequestPacketHandle loginRequestPacketHandle;
    @Autowired
    private MessageRequestPacketHandle messageRequestPacketHandle;
    @Autowired
    private AuthHandle authHandle;

    @PostConstruct
    public void start() {
        // 负责接收连接，并将连接绑定到workGroup上处理
        bossGroup = new NioEventLoopGroup();
        // 负责轮询监听各个连接是否有数据到来，读取数据执行业务处理器
        workGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4, -11, 0));
                        ch.pipeline().addLast(new PacketEncodeHandle());
                        ch.pipeline().addLast(new PacketDecodeHandle());
                        ch.pipeline().addLast(loginRequestPacketHandle);
                        ch.pipeline().addLast(authHandle);
                        ch.pipeline().addLast(messageRequestPacketHandle);
                    }
                })
                //表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.TCP_NODELAY, true);
        bindServerPort(serverBootstrap, port);
        log.info("nettyServerSocket startup listener on ");
    }

    /**
     * 绑定端口号
     *
     * @param serverBootstrap
     * @param port
     */
    private void bindServerPort(ServerBootstrap serverBootstrap, final int port) {
        ChannelFuture channelFuture = serverBootstrap.bind(port);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.info("nettyServerSocket bind port success port={}", port);
            } else {
                // 端口被占用，递增端口号，重试绑定
                int newPort = port + 1;
                bindServerPort(serverBootstrap, newPort);
            }
        });
    }

    @PreDestroy
    public void close() {
        log.info("nettyServerSocket close ,start to close netty server");
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
    }
}
