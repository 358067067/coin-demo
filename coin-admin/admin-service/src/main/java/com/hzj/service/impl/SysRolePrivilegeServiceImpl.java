package com.hzj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.SysMenu;
import com.hzj.domain.SysPrivilege;
import com.hzj.domain.SysRolePrivilege;
import com.hzj.mapper.SysRolePrivilegeMapper;
import com.hzj.service.SysMenuService;
import com.hzj.service.SysPrivilegeService;
import com.hzj.service.SysRolePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege> implements SysRolePrivilegeService{

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @Override
    public List<SysMenu> findSysMenuAndPrivileges(Long roleId) {
        List<SysMenu> list = sysMenuService.list();
        if (CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        List<SysMenu> rootMenus = list.stream()
                                    .filter(sysMenu -> sysMenu.getParentId() == null)
                                    .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(rootMenus))
            return Collections.emptyList();
        List<SysMenu> subMenus = new ArrayList<>();
        for (SysMenu rootMenu : rootMenus) {
            subMenus.addAll(getChildMenus(rootMenu.getId(), roleId, list));
        }
        return null;
    }

    /**
     * 查询子菜单
     * @param id
     * @param roleId
     * @param sources
     * @return
     */
    private List<SysMenu> getChildMenus(Long id, Long roleId, List<SysMenu> sources) {
        List<SysMenu> childs = new ArrayList<>();
        for (SysMenu source: sources) {
            if (source.getParentId() == id) {
                childs.add(source);
                source.setChilds(getChildMenus(source.getId(), roleId, sources));
                List<SysPrivilege> sysPrivilegeList = sysPrivilegeService.getAllSysPrivilege(source.getId(), roleId);
                source.setPrivileges(sysPrivilegeList);
            }
        }
        return childs;
    }
}
