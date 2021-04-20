package com.hzj.service;

import com.hzj.domain.SysMenu;
import com.hzj.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzj.model.RolePrivilegesParam;

import java.util.List;

public interface SysRolePrivilegeService extends IService<SysRolePrivilege>{

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuAndPrivileges(Long roleId);

    /**
     * 授予角色权限
     * @param rolePrivilegesParam
     * @return
     */
    boolean grantPrivileges(RolePrivilegesParam rolePrivilegesParam);
}
