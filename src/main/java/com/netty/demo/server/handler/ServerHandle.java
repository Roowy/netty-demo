//package com.netty.demo.server.handler;
//
//import com.netty.demo.server.protocol.ProtocolConstant;
//import com.netty.demo.server.protocol.coder.PacketDecode;
//import com.netty.demo.server.protocol.coder.PacketEncode;
//import com.netty.demo.server.protocol.packet.*;
//import com.netty.demo.server.protocol.serialize.SerializerAlgorithm;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//
///**
// * @author wangyi
// * @description: 服务端登录处理
// * @date 2019/8/20 18:17
// */
//@Slf4j
//@Component
//public class ServerHandle extends ChannelInboundHandlerAdapter {
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        // 服务端读数据
//        ByteBuf byteBuf = (ByteBuf) msg;
//        Packet packet = PacketDecode.decode(byteBuf);
//        // 定义响应包
//        Packet responsePacket = null;
//        if (packet instanceof LoginRequestPacket) {
//            log.info("客户端登录信息：{}", packet);
//            // 登录校验
//            if (validLoginReques((LoginRequestPacket) packet)) {
//                // 保存登录状态
//                ctx.channel().attr(ProtocolConstant.LOGIN).set(true);
//                responsePacket = LoginResponsePacket.of(true, "登录成功！");
//            } else {
//                ctx.channel().attr(ProtocolConstant.LOGIN).set(false);
//                responsePacket = LoginResponsePacket.of(false, "登录失败，账号或密码错误！");
//            }
//        } else {
//            // 不是登录包，先判断是否登录
//            if (Objects.equals(Boolean.TRUE, ctx.channel().attr(ProtocolConstant.LOGIN).get())) {
//                if (packet instanceof MessageRequestPacket) {
//                    log.info("服务端收到客户端发送的消息：{}" + packet);
//                    responsePacket = MessageResponsePacket.of("我是服务端，我已收到客户端发送的消息：" + ((MessageRequestPacket) packet).getMessage());
//                }
//            } else {
//                log.warn("invalid login request packet={}", packet);
//                responsePacket = LoginResponsePacket.of(false, "错误的登录数据包！");
//            }
//        }
//        // 登录响应数据
//        ByteBuf responseBuf = PacketEncode.encode(ctx.alloc(), responsePacket, SerializerAlgorithm.JSON);
//        ctx.channel().writeAndFlush(responseBuf);
//    }
//
//    /**
//     * 登录校验
//     *
//     * @param requestPacket
//     * @return
//     */
//    private boolean validLoginReques(LoginRequestPacket requestPacket) {
//        if (Objects.equals("admin", requestPacket.getUsername()) && Objects.equals("123456", requestPacket.getPassword())) {
//            return true;
//        }
//        return false;
//    }
//}
