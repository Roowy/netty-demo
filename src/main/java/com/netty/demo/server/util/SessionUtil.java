package com.netty.demo.server.util;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/27 19:35
 */
public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    static {
        new Thread(() -> {
            while (true){
                if (Objects.nonNull(userIdChannelMap)) {
                    System.out.println("###########################" + userIdChannelMap.size() + "###########################");
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
            }
        }).start();
    }

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(ProtocolConstant.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(ProtocolConstant.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(ProtocolConstant.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(ProtocolConstant.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
