package com.netty.demo.console;

import com.netty.demo.server.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.*;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/28 11:21
 */
public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> consoleCommandMap;
    private List<String> commandList;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("sendToGroup", new SendToGroupConsoleCommand());
        commandList = new ArrayList<>();
        commandList.add("sendToUser");
        commandList.add("logout");
        commandList.add("createGroup");
        commandList.add("joinGroup");
        commandList.add("quitGroup");
        commandList.add("listGroupMembers");
        commandList.add("sendToGroup");
    }

    @Override
    public void exec(Channel channel) {
        //  获取第一个指令
        System.out.println("请输入指令：" + commandList);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
        waitForLoginResponse();
    }
}
