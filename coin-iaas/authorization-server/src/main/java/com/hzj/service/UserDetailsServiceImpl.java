package com.hzj.service;

import com.hzj.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType))
            throw new AuthenticationServiceException("登录类型不能为空");
        UserDetails userDetails = null;
        try {
            String grantType = requestAttributes.getRequest().getParameter("grant_type");
            if (LoginConstant.REFRESH_TYPE.equals(grantType.toUpperCase()))
                username = AdjustUsername(username, loginType);
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    userDetails = LoadSysUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails = LoadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException(String.format("暂未支持当前登录方式:%s", loginType));
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            // 用户不存在
            throw new UsernameNotFoundException(String.format("用户名:%s不存在", username));
        }
        return userDetails;
    }

    /**
     * id转换成username
     * @param username 传入时可能是id
     * @param loginType
     * @return
     */
    private String AdjustUsername(String username, String loginType) {
        if (LoginConstant.ADMIN_TYPE.equals(loginType))
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        if (LoginConstant.MEMBER_TYPE.equals(loginType))
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        return username;
    }

    /**
     * 用户
     *
     * @param username
     * @return
     */
    private UserDetails LoadMemberUserByUsername(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, (rs, i) -> {
            if (rs.wasNull())
                throw new UsernameNotFoundException(String.format("用户名:%s不存在", username));
            long id = rs.getLong("id");
            String password = rs.getString("password");
            int status = rs.getInt("status");
            return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }, username, username);
    }

    /**
     * 管理员
     *
     * @param username
     * @return
     */
    private UserDetails LoadSysUserByUsername(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (rs, i) -> {
            if (rs.wasNull())
                throw new UsernameNotFoundException(String.format("用户名:%s不存在", username));
            long id = rs.getLong("id");
            String password = rs.getString("password");
            int status = rs.getInt("status");
            return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    GetSysUserPermissions(id)
            );
        }, username);
    }

    /**
     * 查询用户权限
     *
     * @param id
     * @return
     */
    private Collection<? extends GrantedAuthority> GetSysUserPermissions(long id) {
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null;
        //是否超级管理员
        if (LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)) {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        }
        if (permissions == null || permissions.isEmpty())
            return Collections.emptyList();
        return permissions
                .stream()
                .distinct() //去重
                .map(perm -> new SimpleGrantedAuthority(perm))
                .collect(Collectors.toSet());
    }
}
