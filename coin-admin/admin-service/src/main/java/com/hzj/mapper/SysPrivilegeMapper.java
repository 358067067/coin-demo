package com.hzj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzj.domain.SysPrivilege;

import java.util.Set;

public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    Set<Long> getPrivilegesByRoleId(Long roleId);
}