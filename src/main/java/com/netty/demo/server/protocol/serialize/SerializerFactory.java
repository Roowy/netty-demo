package com.netty.demo.server.protocol.serialize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyi
 * @description: 序列化算法工厂
 * @date 2019/8/20 16:49
 */
public class SerializerFactory {
    private static final Map<Byte, Serializer> serializerMap;

    static {
        serializerMap = new HashMap<>();
        serializerMap.put(SerializerAlgorithm.JSON, Serializer.DEFAULT);
    }

    /**
     * 根据序列化类型得到序列化对象
     *
     * @param algorithm
     * @return
     */
    public static Serializer getSerializer(Byte algorithm) {
        return serializerMap.get(algorithm);
    }
}
