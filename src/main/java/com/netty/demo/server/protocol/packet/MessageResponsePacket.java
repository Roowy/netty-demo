package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/20 19:52
 */
@Data
public class MessageResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

    public static MessageResponsePacket of(String message) {
        MessageResponsePacket packet = new MessageResponsePacket();
        packet.setMessage(message);
        return packet;
    }
}
