package com.netty.demo.server.protocol.serialize;

/**
 * @author wangyi
 * @description: 序列化接口
 * @date 2019/8/20 16:35
 */
public interface Serializer {
    /**
     * 默认序列化算法
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
