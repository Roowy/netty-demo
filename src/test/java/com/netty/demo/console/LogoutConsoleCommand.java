package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description: 登录指令
 * @date 2019/8/28 11:05
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
