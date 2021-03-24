package com.hzj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.SysRole;
import com.hzj.mapper.SysRoleMapper;
import com.hzj.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 判断用户是否为超级的管理员
     * @param userId
     * @return
     */
    @Override
    public boolean isSuperAdmin(Long userId) {
        String roleCode = sysRoleMapper.getUserRoleCode(userId);
        if (!StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN"))
            return true;
        return false;
    }
}
