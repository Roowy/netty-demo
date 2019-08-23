package com.netty.demo.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/19 16:49
 */
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        new Thread(() -> {
            while (true) {
                try {
                    // (1)阻塞方法获取新连接
                    Socket socket = serverSocket.accept();
                    // (2)为每一个连接创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len = 0;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // (3)按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(Thread.currentThread().toString() + new String(data, 0, len));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
