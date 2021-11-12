package com.ktjiaoyu.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */
public interface SysUserService {

    //查询角色列表
    public SysUser seleRoleName(String usrName,String usrPassword);

    //分页显示
    public IPage<SysUser> findPage(IPage<SysUser> page, QueryWrapper<SysUser> wrapper);

    public int count(String usrName,@Param("usrRoleId") Integer usrRoleId);

    /**
     * 查询用户列表
     * @return
     */
    public List<SysUser> list(SysUser sysUser);

    //根据id查询用户信息
    public SysUser findById(Integer usrId);

    //修改
    public int updateInfo(SysUser sysUser);

    //新增
    public int insertInfo(SysUser sysUser);

    //删除
    public int delInfo(Integer usrId);

    //根据usrRoleId删除
    public int deleRoleId(Integer usrRoleId);

    public SysUser getUser(String usrName);

    //根据用户名 查询用户对象
    public SysUser getUserByUsrName(String usrName);

    //加密方法
    public String encryptPassword(Object plaintextPassword);
}
