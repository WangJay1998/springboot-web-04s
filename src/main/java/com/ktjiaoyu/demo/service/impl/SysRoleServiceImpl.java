package com.ktjiaoyu.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.mapper.SysRightMapper;
import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRole;
import com.ktjiaoyu.demo.mapper.SysRoleMapper;
import com.ktjiaoyu.demo.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private  SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRightMapper sysRightMapper;

    @Override
    public List<SysRole> findroleName() {
        return sysRoleMapper.selectList(null);
    }

    @Override
    public IPage<SysRole> findList(IPage<SysRole> page, QueryWrapper<SysRole> wrapper) {
        return sysRoleMapper.selectPage(page,wrapper);
    }

    @Override
    public int addInfo(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public SysRole findRoleById(Integer roleId) {
        return sysRoleMapper.selectById(roleId);
    }

    @Override
    public int updateInfo(SysRole sysRole) {
        return sysRoleMapper.updateById(sysRole);
    }

    @Override
    public int deleInfo(Integer roleId) {
        return sysRoleMapper.deleteById(roleId);
    }

    @Override
    public List<SysRole> findAllRoles() {
        return sysRoleMapper.selectList(null);
    }

    @Override
    public List<SysRight> findAllRights() {
        return sysRightMapper.selectList(null);
    }

    @Override
    public List<SysRight> findRightsByRole(QueryWrapper<SysRight> wrapper) {
        return sysRightMapper.findRightsByRoles(wrapper);
    }

    @Override
    public SysRole selectRoleByRoleId(Integer roleId) {
        QueryWrapper wrapper =new QueryWrapper(roleId);
        wrapper.eq("role_id",roleId);
        return sysRoleMapper.selectOne(wrapper);
    }

}
