package com.hzj.controller;

import com.hzj.model.LoginResult;
import com.hzj.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录controller")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation(value ="管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "username"),
            @ApiImplicitParam(name = "password", value = "password")
    })
    public LoginResult login(@RequestParam String username, @RequestParam String password) {
        return sysLoginService.login(username, password);
    }
}
