package com.hzj.model;

import com.hzj.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录结果")
public class LoginResult {

    /**
     * 保存来自iaas服务的token
     */
    @ApiModelProperty(value = "保存来自iaas服务的token")
    private String token;

    /**
     * 菜单数据
     */
    @ApiModelProperty(value = "菜单数据")
    private List<SysMenu> menus;

    /**
     * 权限数据
     */
    @ApiModelProperty(value = "权限数据")
    private List<SimpleGrantedAuthority> authorities;
}
