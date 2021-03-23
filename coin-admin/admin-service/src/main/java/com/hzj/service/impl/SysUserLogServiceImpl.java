package com.hzj.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.SysUserLog;
import com.hzj.mapper.SysUserLogMapper;
import com.hzj.service.SysUserLogService;
@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog> implements SysUserLogService{

}
