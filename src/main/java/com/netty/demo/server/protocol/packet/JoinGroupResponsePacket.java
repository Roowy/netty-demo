package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description: 加群响应包
 * @date 2019/8/28 15:53
 */
@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
