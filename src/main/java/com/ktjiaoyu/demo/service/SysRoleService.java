package com.ktjiaoyu.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */
public interface SysRoleService  {
      //查找角色名
      public List<SysRole> findroleName();

      //显示列表
      public IPage<SysRole> findList(IPage<SysRole> page, QueryWrapper<SysRole> wrapper);

      //新增
      public int addInfo(SysRole sysRole);

      //根据id显示信息
      public SysRole findRoleById(Integer roleId);

      //修改信息
      public int updateInfo(SysRole sysRole);

      //删除信息
      public int deleInfo(Integer roleId);


      //查询所有角色
      public List<SysRole> findAllRoles();

      //权限查询
      public List<SysRight> findAllRights();

      public List<SysRight> findRightsByRole(QueryWrapper<SysRight> wrapper);

      SysRole selectRoleByRoleId(Integer roleId);
}
