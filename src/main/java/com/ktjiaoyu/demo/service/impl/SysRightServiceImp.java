package com.ktjiaoyu.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.mapper.SysRightMapper;
import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRole;
import com.ktjiaoyu.demo.pojo.SysUser;
import com.ktjiaoyu.demo.service.SysRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("sysRightService")
public class SysRightServiceImp implements SysRightService {

    @Autowired
    private SysRightMapper sysRightMapper;

    @Override
    public SysRight selectRightsByRightId(String rightId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("right_code",rightId);
        return sysRightMapper.selectOne(wrapper);
    }

    @Override
    public List<SysRight> list() {
        QueryWrapper<SysRight> wrapper=new QueryWrapper<>();
        return sysRightMapper.selectList(wrapper);
    }

    @Override
    public IPage<SysRight> findPage(IPage<SysRight> page, QueryWrapper<SysRight> wrapper) {
        return sysRightMapper.selectPage(page,wrapper);
    }

    @Override
    public int addInfo(SysRight sysRight) {
        return sysRightMapper.insert(sysRight);
    }

    @Override
    public int yzsj(String rightCode) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("right_code",rightCode);
        return sysRightMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleRight(String rightCode) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("right_code",rightCode);
        return sysRightMapper.delete(wrapper);
    }

    @Override
    public int deleRightByrightParentCode(String rightParentCode) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("right_parent_code",rightParentCode);
        return sysRightMapper.delete(wrapper);
    }

    @Override
    public SysRight selectByRightCode(String rightCode) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("right_code",rightCode);
        return sysRightMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateInfo(SysRight sysRight) {
        return sysRightMapper.updateById(sysRight);
    }

    @Override
    public List<SysRight> selectByParentCode(String rightCode) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("right_parent_code",rightCode);
        return sysRightMapper.selectList(queryWrapper);
    }
}
