package com.ktjiaoyu.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRole;
import com.ktjiaoyu.demo.pojo.SysUser;

import java.util.List;

public interface SysRightService {

  public SysRight selectRightsByRightId(String rightId);

  //查询所有权限资源
  List<SysRight> list();

  //分页显示
  public IPage<SysRight> findPage(IPage<SysRight> page, QueryWrapper<SysRight> wrapper);

  //添加资源
  public int addInfo(SysRight sysRight);

  //验证有没有数据
  public int yzsj(String rightCode);

  //异步删除
  public int deleRight(String rightCode);          //根据code删除

  //
  public int deleRightByrightParentCode(String rightParentCode);    //根据父类code删除

  //根据code查询对象
  public SysRight selectByRightCode(String rightCode);

  //修改菜单资源
  public int updateInfo(SysRight sysRight);

  public List<SysRight> selectByParentCode(String rightCode);         //根据父类code查询
}
