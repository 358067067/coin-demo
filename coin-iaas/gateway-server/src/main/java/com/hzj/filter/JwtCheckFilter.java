package com.hzj.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${no.require.urls:/admin/login")
    private Set<String> noRequireTokenUris;

    /**
     * 拦截器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 先判断是否需要token认证
        if (isRequireToken(exchange)) {
            return chain.filter(exchange);
        }
        // 解析token
        String token = getToken(exchange);
        // 判断token
        if (StringUtils.isEmpty(token)) {
            return buildNoAuthorizationResult(exchange);
        }
        Boolean hasKey = stringRedisTemplate.hasKey(token);// 检查Redis中是否存在token或过期
        if (hasKey == null || hasKey) {
            return buildNoAuthorizationResult(exchange);
        }
        return chain.filter(exchange);
    }

    /**
     * 返回错误信息
     *
     * @param exchange
     * @return
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "NoAuthorization");
        jsonObject.put("errorMsg", "Token is Null or Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    /**
     * 从请求头取出token（记得要去掉token头部的bearer ）
     *
     * @param exchange
     * @return
     */
    private String getToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("bearer ", "");
    }

    /**
     * 是否需要token认证
     *
     * @param exchange
     * @return
     */
    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (noRequireTokenUris.contains(path))
            return false;
        return true;
    }

    /**
     * 排序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
