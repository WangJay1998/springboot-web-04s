package com.ktjiaoyu.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ktjiaoyu.demo.pojo.SysUser;
import com.ktjiaoyu.demo.mapper.SysUserMapper;
import com.ktjiaoyu.demo.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */
@Service("sysUserService")
@CacheConfig(cacheNames = "sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser seleRoleName(String usrName, String usrPassword) {
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        Md5Hash md5Hash=new Md5Hash(usrPassword,"czkt",1024);
        wrapper.eq("usr_name",usrName).eq("usr_password",usrPassword);
        return sysUserMapper.seleRoleName(usrName,usrPassword);
    }

    @Override
    public IPage<SysUser> findPage(IPage<SysUser> page, QueryWrapper<SysUser> wrapper) {
        return sysUserMapper.selectPage(page,wrapper);
    }

    @Override
    public int count(String usrName, Integer usrRoleId) {
        return sysUserMapper.count(usrName,usrRoleId);
    }

    @Override
    public List<SysUser> list(SysUser sysUser) {
        return sysUserMapper.list(sysUser);
    }

    @Override
    public SysUser findById(Integer usrId) {
        return sysUserMapper.selectById(usrId);
    }

    @Override
    public int updateInfo(SysUser sysUser) {
        return sysUserMapper.updateById(sysUser);
    }

    @Override
    public int insertInfo(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public int delInfo(Integer usrId) {
        return sysUserMapper.deleteById(usrId);
    }

    @Override
    public int deleRoleId(Integer usrRoleId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("usr_role_id",usrRoleId);
        return sysUserMapper.delete(queryWrapper);
    }

    @Override
    @Cacheable(value = "getUser1", keyGenerator = "keyGenerator")
    public SysUser getUser(String usrName) {
        QueryWrapper<SysUser> wrapper=new QueryWrapper<SysUser>();
        wrapper.eq("usr_name",usrName);
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public SysUser getUserByUsrName(String usrName) {
//        QueryWrapper<SysUser> wrapper=new QueryWrapper<SysUser>();
//        wrapper.eq("usr_name",usrName);
        return sysUserMapper.findUserByUsrName(usrName);
    }

    @Override
    public String encryptPassword(Object plaintextPassword) {
        String salt="czkt";
        Md5Hash md5Hash=new Md5Hash(plaintextPassword,salt,1024);
        return md5Hash.toString();
    }
}
