package com.hzj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.SysRole;
import com.hzj.mapper.SysRoleMapper;
import com.hzj.service.SysRoleService;
import org.springframework.stereotype.Service;
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Override
    public boolean isSuperAdmin(Long userId) {
        return false;
    }
}
