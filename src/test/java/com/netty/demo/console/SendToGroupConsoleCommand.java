package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 18:48
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel) {
        System.out.print("发送消息给某个群组,群id和消息：");
        Scanner scanner = new Scanner(System.in);
        String toGroupId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));
    }
}
