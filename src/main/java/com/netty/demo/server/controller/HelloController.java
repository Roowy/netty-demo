package com.netty.demo.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyi
 * @description:
 * @date 2019/8/19 16:36
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        log.info("###########################");
        return "hello";
    }
}
