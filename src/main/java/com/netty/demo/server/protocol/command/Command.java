package com.netty.demo.server.protocol.command;

/**
 * @author wangyi
 * @description: 消息命令枚举
 * @date 2019/8/20 16:04
 */
public interface Command {
    /**
     * 登录
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登录响应
     */
    Byte LOGIN_RESPONSE = 2;

    /**
     * 发送消息
     */
    Byte MESSAGE_REQUEST = 3;

    /**
     * 消息响应
     */
    Byte MESSAGE_RESPONSE = 4;
}
