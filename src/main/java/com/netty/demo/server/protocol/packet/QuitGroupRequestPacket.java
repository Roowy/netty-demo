package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description: 退群请求包
 * @date 2019/8/28 15:57
 */
@Data
public class QuitGroupRequestPacket extends Packet {
    /**
     * 群id
     */
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
