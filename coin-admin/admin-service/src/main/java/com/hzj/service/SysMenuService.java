package com.hzj.service;

import com.hzj.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu>{

    /**
     * 用过id查询菜单数据
     * @param userId
     * @return
     */
    List<SysMenu> getMenusByUserId(Long userId);
}
