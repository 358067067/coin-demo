package com.hzj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    /**
     * 从ThreadLocal中获取当前登录用户
     * SecurityContextHolder.getContext().getAuthentication();
     *
     * @param principal
     * @return
     */
    @GetMapping("/user/info")
    public Principal UserInfo(Principal principal) {
        return principal;
    }
}
