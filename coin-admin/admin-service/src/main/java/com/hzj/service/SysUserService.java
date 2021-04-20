package com.hzj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysUserService extends IService<SysUser>{

    /**
     * 分页查询员工
     * @param page
     * @param mobile
     * @param username
     * @return
     */
    Page<SysUser> findByPage(Page<SysUser> page, String mobile, String username);

    /**
     * 新增员工
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);
}
