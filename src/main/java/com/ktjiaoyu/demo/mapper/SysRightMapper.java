package com.ktjiaoyu.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ktjiaoyu.demo.pojo.SysRight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRightMapper extends BaseMapper<SysRight> {
    public List<SysRight> findRightsByRoles(QueryWrapper<SysRight> wrapper);
}
