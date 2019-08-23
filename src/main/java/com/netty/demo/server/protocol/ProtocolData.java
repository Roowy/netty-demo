package com.netty.demo.server.protocol;

import lombok.Data;

/**
 * @author wangyi
 * @description: 协议包对象
 * @date 2019/8/20 16:53
 */
@Data
public class ProtocolData {
    /**
     * 开始标记
     */
    private int startFlag;

    /**
     * 结束标记
     */
    private int endFlag;

    /**
     * 版本号
     */
    private byte version;

    /**
     * 序列化算法
     */
    private byte algorithm;

    /**
     * 消息命令
     */
    private byte command;

    /**
     * 上下行pb协议数据
     */
    private byte[] data;

    /**
     * data数据长度
     */
    private int len;


}
