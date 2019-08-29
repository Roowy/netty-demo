package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import com.netty.demo.server.session.Session;
import lombok.Data;

import java.util.List;

/**
 * @author wangyi
 * @description: 获取群成员响应包
 * @date 2019/8/28 15:55
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {
    private boolean success;

    private String reason;

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
