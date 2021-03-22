package com.hzj.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hzj.model.WebLog;
import com.hzj.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    @Cached(name = "com.hzj.service.impl.TestServiceImpl", key="#username", cacheType =  CacheType.BOTH)
    public WebLog get(String username) {
        WebLog webLog = new WebLog();
        webLog.setUsername("hzj");
        webLog.setResult("ok");
        return webLog;
    }
}
