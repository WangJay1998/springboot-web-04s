package com.ktjiaoyu.demo.config;

import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRoleRight;
import com.ktjiaoyu.demo.pojo.SysUser;
import com.ktjiaoyu.demo.service.SysRightService;
import com.ktjiaoyu.demo.service.SysRoleRightService;
import com.ktjiaoyu.demo.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
    @Autowired
    private SysRoleRightService sysRoleRightService;
    @Autowired
    private SysRightService sysRightService;


    //限制登录次数
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //redis中数据的key的前缀
    private String SHIRO_LOGIN_COUNT="shiro_login_count_";  //登录计数
    private String SHIRO_IS_LOCK="shiro_is_lock_";         //锁定用户登录



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("调用方法获取信息！");
        //获得身份信息
//        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
//        String usrName=token.getUsername();
        String usrName = (String) authenticationToken.getPrincipal();
        System.out.println(usrName);
        //每次访问，登录次数+1
        ValueOperations<String,String> opsForValue =stringRedisTemplate.opsForValue();
        opsForValue.increment(SHIRO_LOGIN_COUNT+usrName,1);
        //计数大于5，设置用户被锁定一小时，清空登录技术
        if (Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+usrName))>5){
            opsForValue.set(SHIRO_IS_LOCK+usrName,"Lock");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+usrName,1, TimeUnit.HOURS);   //设置锁定账号
            stringRedisTemplate.delete(SHIRO_LOGIN_COUNT+usrName);     //清空
        }
        if("Lock".equals(opsForValue.get(SHIRO_IS_LOCK+usrName))){
            throw  new DisabledAccountException();
        }
        SysUser sysUser =sysUserService.getUserByUsrName(usrName);
        System.out.println("sssss"+sysUser);
        if(sysUser==null){
            throw new UnknownAccountException();  //账号错误
        }
        if(sysUser.getUsrFlag()==null||sysUser.getUsrFlag().intValue()==0){
            throw new LockedAccountException();  //账号锁定
        }
        SimpleAccount info= new SimpleAccount(sysUser,sysUser.getUsrPassword(),ByteSource.Util.bytes("czkt"),getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("调用方法获取信息！111");
        //获得权限信息
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();


             HashSet<String> roles = new HashSet<>();
        roles.add(sysUser.getRoleName());
        HashSet<String> perms = new HashSet<>();

        //根据角色id查询权限id
        List<SysRoleRight> sysRoleRights = sysRoleRightService.selectRoleRightsByRoleId(sysUser.getUsrRoleId());
        //根据权限id查找出权限信息
        for (SysRoleRight roleRight : sysRoleRights) {
            SysRight sysRight = sysRightService.selectRightsByRightId(roleRight.getRfRightCode());
            perms.add(sysRight.getRightText());
        }

        //动态授权:授予主体用户相应的角色和权限
        info.setRoles(roles);
        info.setStringPermissions(perms);


        return info;
    }
}
