package com.netty.demo.server.protocol;

import com.netty.demo.server.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author wangyi
 * @description: 协议常量
 * @date 2019/8/20 16:56
 */
public interface ProtocolConstant {
    /**
     * 开始标记
     */
    int START_FLAG = 0x12345678;
    /**
     * 结束标记
     */
    int END_FLAG = 0x87654321;

    /**
     * 版本号
     */
    byte VERSION = 0x1;

    /**
     * 最小数据包长度
     */
    int MIN_PROTOCOL_LEN = 15;

    /**
     * 用户会话属性
     */
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
