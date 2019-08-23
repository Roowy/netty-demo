package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

import java.util.UUID;

/**
 * @author wangyi
 * @description: 登录数据包
 * @date 2019/8/20 16:27
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public static LoginRequestPacket of(String username, String password) {
        return of(UUID.randomUUID().toString(), username, password);
    }

    public static LoginRequestPacket of(String userId, String username, String password) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setPassword(password);
        requestPacket.setUserId(userId);
        requestPacket.setUsername(username);
        return requestPacket;
    }
}
