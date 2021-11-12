package com.ktjiaoyu.demo.config;

import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.service.SysRoleService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Resource
    private SysRoleService sysRoleService;


    public RedisManager redisManager(){
        RedisManager redisManager=new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    public RedisCacheManager cacheManager(){
        RedisCacheManager cacheManager=new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        //缓存名称
        cacheManager.setPrincipalIdFieldName("usrName");
        //缓存有效时间
        cacheManager.setExpire(1800);
        return cacheManager;
    }

    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO sessionDAO=new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }


    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm=new MyShiroRealm();
        //设置启用缓存，并设置缓存
        myShiroRealm.setCachingEnabled(true);
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setAuthorizationCacheName("authorizationCache");

        //设置密码匹配器
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //注入Realm
        securityManager.setRealm(myShiroRealm());

        //注入缓存管理器
        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){  //shiro过滤器：权限验证
        System.out.println(1);
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //注入SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //权限验证:使用filter控制url的访问
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/main");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");   //没有权限跳到403
        Map<String,String> map=new LinkedHashMap<>();
        //配置可以匿名访问的资源url:静态资源
        map.put("/css/**","anon");
        map.put("/fonts/**","anon");
        map.put("/images/**","anon");
        map.put("/js/**","anon");
        map.put("/localcss/**","anon");

//        map.put("/login","anon");
        map.put("/dologin","anon");     //不用登录即可访问
        map.put("/logout","logout");    //注销过滤器，自动注销

//        map.put("/user/list","perms[用户列表]");    //对应自定义的域添加的权限 info.addStringPermission("用户列表");
//        map.put("/user/add","perms[用户添加]");
//        map.put("/user/edit","perms[用户编辑]");
//        map.put("/user/del","perms[用户删除]");

        //禁用
        List<SysRight> rights = sysRoleService.findAllRights();  //查询资源表
        for (SysRight right : rights) {
            if(right.getRightUrl()!=null && !right.getRightUrl().trim().equals("")){
                map.put(right.getRightUrl(),"perms["+right.getRightText()+"]");
            }
        }
        //配置认证访问:其他资源(url)必须认证通过才能访问
        map.put("/**","authc");      //必须登录才能访问所有资源

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//        map.put("/main","authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//        shiroFilterFactoryBean.setLoginUrl("/");        //拦截功能
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        //使用md5算法进行加密
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1024);
        return  matcher;
    }
}

