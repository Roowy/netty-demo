package com.netty.demo.server.protocol.packet;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wangyi
 * @description: 抽象数据包
 * @date 2019/8/20 16:06
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 0x1;


    /**
     * 消息命令
     *
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
