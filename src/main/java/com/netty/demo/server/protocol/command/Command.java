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

    /**
     * 登出请求
     */
    Byte LOGOUT_REQUEST = 5;

    /**
     * 登出响应
     */
    Byte LOGOUT_RESPONSE = 6;

    /**
     * 创建群聊请求
     */
    Byte CREATE_GROUP_REQUEST = 7;

    /**
     * 创建群聊响应
     */
    Byte CREATE_GROUP_RESPONSE = 8;

    /**
     * 获取群用户请求
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 9;

    /**
     * 获取群用户响应
     */
    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;

    /**
     * 加群请求
     */
    Byte JOIN_GROUP_REQUEST = 11;

    /**
     * 加群响应
     */
    Byte JOIN_GROUP_RESPONSE = 12;

    /**
     * 退群请求
     */
    Byte QUIT_GROUP_REQUEST = 13;

    /**
     * 退群响应
     */
    Byte QUIT_GROUP_RESPONSE = 14;

    /**
     * 群消息请求
     */
    Byte GROUP_MESSAGE_REQUEST = 15;

    /**
     * 群消息响应
     */
    Byte GROUP_MESSAGE_RESPONSE = 16;
}
