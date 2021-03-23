package com.hzj.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.WebConfig;
import com.hzj.mapper.WebConfigMapper;
import com.hzj.service.WebConfigService;
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService{

}
