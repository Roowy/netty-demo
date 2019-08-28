package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;

/**
 * @author wangyi
 * @description: 登录请求包
 * @date 2019/8/28 11:05
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
