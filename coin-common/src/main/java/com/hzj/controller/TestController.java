package com.hzj.controller;

import com.hzj.model.R;
import com.hzj.model.WebLog;
import com.hzj.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(tags = "CoinCommon")
public class TestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private TestService testService;

    @GetMapping("/common/test")
    @ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "参数1", type = "String", paramType = "query", example = "paramValue"),
            @ApiImplicitParam(name = "param2", value = "参数2", type = "String", paramType = "query", example = "paramValue")
    })
    public R<String> testMethod(String param1, String param2) {
        return R.ok("ok");
    }

    @GetMapping("/common/date")
    @ApiOperation(value = "日期格式化", authorizations = {@Authorization("Authorization")})
    public R<Date> testDate() {
        return R.ok(new Date());
    }

    @GetMapping("/common/redis")
    @ApiOperation(value = "redis测试", authorizations = {@Authorization("Authorization")})
    public R<String> testRedis() {
        WebLog webLog = new WebLog();
        webLog.setResult("ok");
        webLog.setMethod("com.hzj.controller.TestController.testRedis");
        webLog.setUsername("hzj");
        redisTemplate.opsForValue().set("com.hzj.domain.Weblog", webLog);
        return R.ok("ok");
    }

    @GetMapping("/common/cache")
    @ApiOperation(value = "cache测试", authorizations = {@Authorization("Authorization")})
    public R<String> testCache(String username) {
        WebLog webLog = testService.get(username);
        System.out.println(webLog);
        return R.ok("ok");
    }
}
