package com.hzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.SysUser;
import com.hzj.domain.SysUserRole;
import com.hzj.mapper.SysUserMapper;
import com.hzj.service.SysUserRoleService;
import com.hzj.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Page<SysUser> findByPage(Page<SysUser> page, String mobile, String username) {
        Page<SysUser> page1 = page(page, new LambdaQueryWrapper<SysUser>()
                .eq(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
                .like(!StringUtils.isEmpty(username), SysUser::getFullname, username));
        List<SysUser> records = page1.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            for (SysUser record : records) {
                List<SysUserRole> list = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId()));
                if (!CollectionUtils.isEmpty(list))
                    record.setRole_strings(list.stream()
                            .map(sysUserRole -> sysUserRole.getRoleId().toString())
                            .collect(Collectors.joining(",")));
            }
        }
        return page1;
    }

    @Override
    @Transactional
    public boolean addUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        String role_strings = sysUser.getRole_strings();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        sysUser.setPassword(encode);
        boolean save = save(sysUser);
        if (save) {
            if (!StringUtils.isEmpty(role_strings)) {
                String[] roleIds = role_strings.split(",");
                List<SysUserRole> sysUserRoleList = new ArrayList<>();
                for (String roleId : roleIds) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRoleList.add(sysUserRole);
                }
                sysUserRoleService.saveBatch(sysUserRoleList);
            }
        }
        return save;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean b = super.removeByIds(idList);
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, idList));
        return b;
    }
}
