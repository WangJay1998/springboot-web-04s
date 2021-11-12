package com.ktjiaoyu.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */

public interface SysUserMapper extends BaseMapper<SysUser> {

    public SysUser seleRoleName(String usrName, String usrPassword);

//    //分页查询
//    public IPage<SysUser> selePage(IPage<SysUser> page, QueryWrapper<SysUser> wrapper);
    /**
     * 查询用户列表
     * @return
     */
    public List<SysUser> list(SysUser sysUser);

    /**
     * 查询总数据
     * @param
     * @return
     */
    public int count(@Param("usrName") String usrName, @Param("usrRoleId")Integer usrRoleId);

    //根据用户名 查询用户对象
    public SysUser findUserByUsrName(String usrName);
}
