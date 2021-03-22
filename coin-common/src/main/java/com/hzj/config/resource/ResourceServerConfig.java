package com.hzj.config.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().disable()
                .authorizeRequests().antMatchers(
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/webjars/**",
                "/swagger-ui.html"
        ).permitAll()
                .antMatchers("/**").authenticated()
                .and().headers().cacheControl();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore());
    }

    private TokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
        return jwtTokenStore;
    }

    @Bean // 放入ioc 方便调用
    public JwtAccessTokenConverter accessTokenConverter() {
        // 验证token
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.txt");
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            String s = new String(bytes, "UTF-8");
            jwtAccessTokenConverter.setVerifierKey(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwtAccessTokenConverter;
    }
}
