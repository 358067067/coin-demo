package com.hzj.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.domain.SysUser;
import com.hzj.model.R;
import com.hzj.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@Api(tags = "员工管理")
@RestController
@RequestMapping("/users")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示数"),
            @ApiImplicitParam(name = "mobile", value = "手机号"),
            @ApiImplicitParam(name = "username", value = "员工姓名")
    })
    @PreAuthorize("hasAuthority('sys_user_query')")
    public R<Page<SysUser>> findByPage(@ApiIgnore Page<SysUser> page, String mobile, String username) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUser> rs = sysUserService.findByPage(page, mobile, username);
        return R.ok(rs);
    }

    @PostMapping
    @ApiOperation(value = "新增员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "sysUser")
    })
//    @PreAuthorize("hasAuthority('sys_user_create')")
    public R add(@RequestBody SysUser sysUser) {
        boolean isOk = sysUserService.addUser(sysUser);
        if (isOk)
            return R.ok();
        return R.fail();
    }

    @PutMapping
    @ApiOperation(value = "修改员工")
    public R update(@RequestBody SysUser sysUser) {
        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除员工")
    public R delete(@RequestBody long ids[]) {
        boolean b = sysUserService.removeByIds(Arrays.asList(ids));
        if (b)
            return R.ok();
        return R.fail();
    }
}
