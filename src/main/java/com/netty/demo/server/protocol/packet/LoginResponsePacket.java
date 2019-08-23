package com.netty.demo.server.protocol.packet;

import com.netty.demo.server.protocol.command.Command;
import lombok.Data;

/**
 * @author wangyi
 * @description: 登录响应包
 * @date 2019/8/20 18:09
 */
@Data
public class LoginResponsePacket extends Packet {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 登录失败理由
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

    public static LoginResponsePacket of(boolean success, String reason) {
        LoginResponsePacket packet = new LoginResponsePacket();
        packet.setReason(reason);
        packet.setSuccess(success);
        return packet;
    }
}
