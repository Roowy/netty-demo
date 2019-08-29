package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 17:44
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel) {
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();

        System.out.print("输入 groupId，获取群成员列表：");
        Scanner scanner = new Scanner(System.in);
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
