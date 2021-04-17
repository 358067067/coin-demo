package com.hzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.SysPrivilege;
import com.hzj.mapper.SysPrivilegeMapper;
import com.hzj.service.SysPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService{

    @Autowired
    private SysPrivilegeMapper  sysPrivilegeMapper;

    @Override
    public List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId) {
        // 查询菜单下的权限
        List<SysPrivilege> list = list(new LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
        if (CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        // 判断角色是否有该权限
        for (SysPrivilege sysPrivilege : list) {
            Set<Long> currentRoleSysPrivilegeIds = sysPrivilegeMapper.getPrivilegesByRoleId(roleId);
            if (currentRoleSysPrivilegeIds.contains(sysPrivilege.getId()))
                sysPrivilege.setOwn(1);
        }
        return list;
    }
}
