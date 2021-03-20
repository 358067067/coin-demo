package com.hzj.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class GatewayFlowRulesController {
    /**
     * 获取限流策略
     */
    @GetMapping("/gw/flow/rules")
    public Set<GatewayFlowRule> getGatewayFlowRules() {
        return GatewayRuleManager.getRules();
    }

    /**
     * 获取api分组的限流策略
     */
    @GetMapping("/gw/api/groups")
    public Set<ApiDefinition> getApiDefinitions() {
        return GatewayApiDefinitionManager.getApiDefinitions();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello sentinel";
    }
}
