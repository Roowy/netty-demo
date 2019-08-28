package com.netty.demo.server.util;

import java.util.UUID;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 11:37
 */
public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
