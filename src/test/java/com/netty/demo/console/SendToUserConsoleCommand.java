package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 11:13
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel) {
        System.out.println("给某个用户发送消息：");
        Scanner scanner = new Scanner(System.in);
        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
