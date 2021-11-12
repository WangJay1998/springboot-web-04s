package com.ktjiaoyu.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ktjiaoyu.demo.mapper.SysRoleRightMapper;
import com.ktjiaoyu.demo.pojo.SysRoleRight;
import com.ktjiaoyu.demo.service.SysRoleRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SysRoleRightServiceImpl  implements SysRoleRightService {
    @Autowired
    SysRoleRightMapper sysRoleRightMapper;

    @Override
    public List<SysRoleRight> selectRoleRightsByRoleId(Integer roleId) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("rf_role_id",roleId);
        return sysRoleRightMapper.selectList(wrapper);
    }

    @Override
    public int addRoleRight(SysRoleRight sysRoleRight) {
        return sysRoleRightMapper.insert(sysRoleRight);
    }

    @Override
    public int updateRoleRight(SysRoleRight sysRoleRight) {
        return sysRoleRightMapper.updateById(sysRoleRight);
    }

    @Override
    public int deleRoleRight(Integer roleId) {
        return sysRoleRightMapper.deleteById(roleId);
    }

    @Override
    public int addListByCodeAndRoId(String code, Integer roId) {
        SysRoleRight sysRoleRight=new SysRoleRight();
        sysRoleRight.setRfRightCode(code);
        sysRoleRight.setRfRoleId(roId);
        return sysRoleRightMapper.insert(sysRoleRight);
    }

    @Override
    public int deleCodeAndRoId(Integer roId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rf_role_id",roId);
        return sysRoleRightMapper.delete(queryWrapper);
    }

    @Override
    public int delteCode(String code) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rf_right_code",code);
        return sysRoleRightMapper.delete(queryWrapper);
    }

    @Override
    public int deleListByCodeAndRoId(String code, Integer roId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rf_right_code",code);
        queryWrapper.eq("rf_role_id",roId);
        return sysRoleRightMapper.delete(queryWrapper);
    }

    @Override
    public List<SysRoleRight> selectByCode(String code) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rf_right_code",code);
        return sysRoleRightMapper.selectList(queryWrapper);
    }
}
