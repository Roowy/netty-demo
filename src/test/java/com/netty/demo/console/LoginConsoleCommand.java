package com.netty.demo.console;

import com.netty.demo.server.protocol.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangyi
 * @description: 登录指令
 * @date 2019/8/28 11:03
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec( Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        Scanner scanner = new Scanner(System.in);
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }
}
