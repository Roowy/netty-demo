package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyi
 * @description: packet工厂
 * @date 2019/8/20 16:42
 */
public class PacketFactory {

    /**
     * 缓存各种消息对应数据包的类型
     */
    private static final Map<Byte, Class<? extends Packet>> packetClassMap;

    static {
        packetClassMap = new HashMap<>();
        packetClassMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetClassMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetClassMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetClassMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetClassMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetClassMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetClassMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetClassMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetClassMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetClassMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetClassMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetClassMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetClassMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetClassMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetClassMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetClassMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
    }

    /**
     * 根据命令类型获取数据包类型
     *
     * @param command
     * @return
     */
    public static Class<? extends Packet> getPacketClass(Byte command) {
        return packetClassMap.get(command);
    }
}
