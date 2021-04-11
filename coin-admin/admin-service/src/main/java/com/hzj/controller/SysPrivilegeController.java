package com.hzj.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.domain.SysPrivilege;
import com.hzj.model.R;
import com.hzj.service.SysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/privileges")
@Api(tags = "权限管理")
public class SysPrivilegeController {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    /**
     * @param page
     * @return
     * @PreAuthorize("hasAuthority('sys_privilege_query')") 表示访问该业务角色需要权限sys_privilege_query
     */
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示数")
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    public R<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page) {
        //排序
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return R.ok(sysPrivilegePage);
    }

    @PostMapping
    @ApiOperation(value = "新增权限")
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "数据")
    })
    public R add(@RequestBody @Validated SysPrivilege sysPrivilege) {
        //从上下文中获取当前用户id(已经从common模块的handler处理过审计信息)
//        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        sysPrivilege.setCreateBy(Long.valueOf(userId));
//        sysPrivilege.setCreated(new Date());
//        sysPrivilege.setLastUpdateTime(new Date());
        boolean save = sysPrivilegeService.save(sysPrivilege);
        if (save)
            return R.ok("新增成功");
        else
            return R.fail("新增失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改权限")
    @PreAuthorize("hasAuthority('sys_privilege_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "数据")
    })
    public R update(@RequestBody @Validated SysPrivilege sysPrivilege) {
        //从上下文中获取当前用户id(已经从common模块的handler处理过审计信息)
//        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        sysPrivilege.setModifyBy(Long.valueOf(userId));
//        sysPrivilege.setLastUpdateTime(new Date());
        boolean save = sysPrivilegeService.updateById(sysPrivilege);
        if (save)
            return R.ok("修改成功");
        else
            return R.fail("修改失败");
    }
}
