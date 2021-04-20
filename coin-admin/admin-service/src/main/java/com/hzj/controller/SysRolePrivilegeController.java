package com.hzj.controller;

import com.hzj.domain.SysMenu;
import com.hzj.model.R;
import com.hzj.model.RolePrivilegesParam;
import com.hzj.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "角色权限配置")
public class SysRolePrivilegeController {

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @GetMapping("/roles_privileges")
    @ApiOperation(value = "查询角色的权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id")
    })
    public R<List<SysMenu>> findSysMenuAndPrivileges(Long roleId) {
        List<SysMenu> sysMenus = sysRolePrivilegeService.findSysMenuAndPrivileges(roleId);
        return R.ok(sysMenus);
    }

    @PostMapping("/grant_privileges")
    @ApiOperation(value = "授予角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolePrivilegesParam", value = "rolePrivilegesParam")
    })
    public R grantPrivileges(@RequestBody RolePrivilegesParam rolePrivilegesParam) {
        boolean isOk = sysRolePrivilegeService.grantPrivileges(rolePrivilegesParam);
        if (isOk)
            return R.ok("操作成功");
        else
            return R.fail("操作失败");
    }
}
