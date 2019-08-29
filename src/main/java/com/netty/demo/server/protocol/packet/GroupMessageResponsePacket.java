package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import com.netty.demo.server.session.Session;
import lombok.Data;

/**
 * @author wangyi
 * @description: 群消息响应包
 * @date 2019/8/28 18:17
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    /**
     * 群id
     */
    private String fromGroupId;

    /**
     * 发送用户
     */
    private Session fromUser;

    /**
     * 消息
     */
    private String message;


    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
