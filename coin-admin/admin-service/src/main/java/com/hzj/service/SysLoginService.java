package com.hzj.service;

import com.hzj.model.LoginResult;

public interface SysLoginService {
    /**
     * 登录
     * @param username
     * @param password
     * @return 登录结果
     */
    LoginResult login(String username, String password);
}
