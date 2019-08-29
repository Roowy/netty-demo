package com.netty.demo.server.util;

import com.netty.demo.server.protocol.ProtocolConstant;
import com.netty.demo.server.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

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
    /**
     * 用户channel
     */
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 群组channel
     */
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    static {
        new Thread(() -> {
            while (true) {
                if (Objects.nonNull(userIdChannelMap)) {
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

        return channel.hasAttr(ProtocolConstant.SESSION) && Objects.nonNull(channel.attr(ProtocolConstant.SESSION).get());
    }

    public static Session getSession(Channel channel) {

        return channel.attr(ProtocolConstant.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }

    /**
     * 绑定群组channel
     *
     * @param groupId
     * @param channelGroup
     */
    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    /**
     * 根据群id获取群组Channel
     *
     * @param groupId
     * @return
     */
    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }

    /**
     * 删除群组channel
     *
     * @param groupId
     * @return
     */
    public static ChannelGroup unBindChannelGroup(String groupId) {
        return groupIdChannelGroupMap.remove(groupId);
    }
}
