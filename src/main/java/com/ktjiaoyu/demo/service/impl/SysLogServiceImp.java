package com.ktjiaoyu.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.mapper.SysLogMapper;
import com.ktjiaoyu.demo.pojo.SysLog;
import com.ktjiaoyu.demo.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogServiceImp implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public IPage<SysLog> list(IPage<SysLog> page, QueryWrapper<SysLog> wrapper) {
        return sysLogMapper.selectPage(page,wrapper);
    }
}
