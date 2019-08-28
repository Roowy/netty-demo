package com.netty.demo.server.session;

import lombok.Data;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/27 19:34
 */
@Data
public class Session {
    /**
     * 用户唯一性标识
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
