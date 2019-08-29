package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description: 加群请求包
 * @date 2019/8/28 15:50
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    /**
     * 群id
     */
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
