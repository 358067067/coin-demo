package com.hzj.service;

import com.hzj.domain.SysMenu;
import com.hzj.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRolePrivilegeService extends IService<SysRolePrivilege>{


    List<SysMenu> findSysMenuAndPrivileges(Long roleId);
}
