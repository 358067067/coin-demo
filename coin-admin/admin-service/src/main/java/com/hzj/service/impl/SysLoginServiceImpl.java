package com.hzj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.hzj.domain.SysMenu;
import com.hzj.feign.JwtToken;
import com.hzj.feign.OAuth2FeignClient;
import com.hzj.model.LoginResult;
import com.hzj.service.SysLoginService;
import com.hzj.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public LoginResult login(String username, String password) {
        log.info("用户{}登录", username);
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        if (tokenResponseEntity.getStatusCode() != HttpStatus.OK)
            throw new ApiException(ApiErrorCode.FAILED);
        JwtToken jwtToken = tokenResponseEntity.getBody();
        log.info("远程调用授权服务器成功,获取的token为{}", JSON.toJSONString(jwtToken,true));
        String token = jwtToken.getAccessToken();

        Jwt jwt = JwtHelper.decode(token);
        String claims = jwt.getClaims();
        JSONObject jwtJSON = JSON.parseObject(claims);

        Long userId = Long.valueOf(jwtJSON.getString("user_name"));
        List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);

        JSONArray authoritiesJsonArray = jwtJSON.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream()
                                                            .map(a -> new SimpleGrantedAuthority(a.toString()))
                                                            .collect(Collectors.toList());
        redisTemplate.opsForValue().set(token,"", jwtToken.getExpiresIn() , TimeUnit.SECONDS);
        return new LoginResult(token, menus, authorities);
    }
}
