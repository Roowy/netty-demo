package com.netty.demo.server.protocol.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author wangyi
 * @description: json序列化算法
 * @date 2019/8/20 16:38
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
