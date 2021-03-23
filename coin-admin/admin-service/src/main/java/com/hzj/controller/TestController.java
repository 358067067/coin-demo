package com.hzj.controller;

import com.hzj.domain.SysUser;
import com.hzj.model.R;
import com.hzj.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "admin模块api测试接口")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "查询用户信息")
    @GetMapping("/user/info/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id")
    })
    public R<SysUser> getSysUserInfo(@PathVariable("id") Long id) {
        SysUser byId = sysUserService.getById(id);
        return R.ok(byId);
    }
}
