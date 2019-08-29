package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description: 获取群成员请求包
 * @date 2019/8/28 15:54
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
