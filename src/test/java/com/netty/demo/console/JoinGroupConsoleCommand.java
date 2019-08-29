package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 16:23
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        System.out.print("输入 groupId，加入群聊：");
        Scanner scanner = new Scanner(System.in);
        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
