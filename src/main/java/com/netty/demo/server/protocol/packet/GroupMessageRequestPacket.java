package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description: 群消息请求包
 * @date 2019/8/28 18:14
 */
@Data
public class GroupMessageRequestPacket extends Packet {

    /**
     * 发送至群id
     */
    private String toGroupId;

    /**
     * 消息
     */
    private String message;

    public GroupMessageRequestPacket() {
    }

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
