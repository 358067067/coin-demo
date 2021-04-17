package com.hzj.service;

import com.hzj.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPrivilegeService extends IService<SysPrivilege>{


    /**
     * 获取该菜单下的权限
     * @param menuId 菜单id
     * @param roleId 角色id
     * @return
     */
    List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId);
}
