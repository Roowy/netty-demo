package com.netty.demo.console;

import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyi
 * @description: 控制台命令执行接口
 * @date 2019/8/28 11:01
 */
public interface ConsoleCommand {
    void exec(Channel channel);

    /**
     * 休眠
     */
    default void waitForLoginResponse() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException ignored) {
        }
    }
}
