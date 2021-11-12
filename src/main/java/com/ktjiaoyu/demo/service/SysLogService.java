package com.ktjiaoyu.demo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.pojo.SysLog;
import com.ktjiaoyu.demo.pojo.SysRole;


public interface SysLogService {
    //分页查询
    public IPage<SysLog> list(IPage<SysLog> page, QueryWrapper<SysLog> wrapper);
}
