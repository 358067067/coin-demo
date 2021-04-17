package com.hzj.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.domain.SysRole;
import com.hzj.model.R;
import com.hzj.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@RequestMapping("/roles")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping
    @ApiOperation(value = "条件分页查询")
    @PreAuthorize("hasAuthority('sys_role_query')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示数"),
            @ApiImplicitParam(name = "name", value = "角色名称")
    })
    public R<Page<SysRole>> findByPage(@ApiIgnore Page<SysRole> page, String name) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(sysRoleService.findByPage(page, name));
    }

    @PostMapping
    @ApiOperation(value = "新增角色")
    @PreAuthorize("hasAuthority('sys_role_create')")
    public R add(@RequestBody @Validated SysRole sysRole) {
        boolean save = sysRoleService.save(sysRole);
        if (save)
            return R.ok("新增成功");
        else
            return R.fail("新增失败");
    }

    @DeleteMapping
    @ApiOperation(value = "删除角色")
    @PreAuthorize("hasAuthority('sys_role_delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "删除角色id的集合")
    })
    public R delete(@RequestBody String[] ids) {
        if (ArrayUtils.isEmpty(ids))
            return R.fail("删除的id不能为空");
        boolean b = sysRoleService.removeByIds(Arrays.asList(ids));
        if (b)
            return R.ok("删除成功");
        else
            return R.fail("删除失败");
    }
}
