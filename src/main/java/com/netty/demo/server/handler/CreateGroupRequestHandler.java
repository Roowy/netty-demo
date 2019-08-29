package com.netty.demo.server.handler;

import com.netty.demo.server.protocol.packet.CreateGroupRequestPacket;
import com.netty.demo.server.protocol.packet.CreateGroupResponsePacket;
import com.netty.demo.server.util.IDUtil;
import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wangyi
 * @description: 创建群聊请求处理器
 * @date 2019/8/28 11:30
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        // 群用户id列表
        List<String> userIdList = createGroupRequestPacket.getUserIdList();
        List<String> userNameList = new ArrayList<>();
        // 1.创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        // 2.筛选出待加入群聊的用户的channel和userName
        userIdList.stream().forEach(id -> {
            Channel channel = SessionUtil.getChannel(id);
            if (Objects.nonNull(channel)) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        });
        // 3.构建创建群聊响应包
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setGroupId(IDUtil.randomId());
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setUserNameList(userNameList);
        // 4.保存群组相关的信息
        SessionUtil.bindChannelGroup(createGroupResponsePacket.getGroupId(), channelGroup);
        // 5.给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);
        log.info("群创建成功，群id 为{} ,群用户：{}", createGroupResponsePacket.getGroupId(), userNameList);
    }
}
