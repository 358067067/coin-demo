package com.hzj.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzj.domain.Notice;
import com.hzj.mapper.NoticeMapper;
import com.hzj.service.NoticeService;
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

}
