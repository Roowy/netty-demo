package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 17:05
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel) {
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();
        System.out.print("输入 groupId，退出群聊：");
        Scanner scanner = new Scanner(System.in);
        String groupId = scanner.next();

        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
