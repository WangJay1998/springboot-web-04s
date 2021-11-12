package com.ktjiaoyu.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktjiaoyu.demo.pojo.SysRoleRight;

import java.util.Collection;
import java.util.List;

public interface SysRoleRightService {
    List<SysRoleRight> selectRoleRightsByRoleId(Integer roleId);

    //添加一条权限
    int addRoleRight(SysRoleRight sysRoleRight);

    //修改一条权限
    int updateRoleRight(SysRoleRight sysRoleRight);

    //删除一条
    int deleRoleRight(Integer roleId);

    //根据code和roId添加权限资源表
    int addListByCodeAndRoId(String code,Integer roId);


    int deleCodeAndRoId(Integer roId);

    //
    int delteCode(String code);

    int deleListByCodeAndRoId(String code,Integer roId);

    List<SysRoleRight> selectByCode(String code);
}
