package com.ktjiaoyu.demo;

import com.ktjiaoyu.demo.pojo.SysUser;
import com.ktjiaoyu.demo.service.SysUserService;
import com.ktjiaoyu.demo2.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTester {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;  //操作字符串数据

    @Resource
    private RedisTemplate redisTemplate;     //操作其它数据类型

    @Test
    public void t1(){
        stringRedisTemplate.opsForValue().set("name","陈明25");
        Assert.assertEquals("陈明25",stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    public void t2(){
        User user=new User("czkt","123456",1,null);
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        operations.set("ktjiaoyu.demo2.user",user);
        User u = operations.get("ktjiaoyu.demo2.user");
        System.out.println("username:"+u.getName());
    }

    @Test
    public void t3() throws InterruptedException {
        User user=new User("陈明","520cm",1,null);
        ValueOperations<String,User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("expire",user,100, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);
        Boolean exists = redisTemplate.hasKey("expire");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("2222222");
        }
    }

    //操作hash
    @Test
    public void t4() {
        HashOperations<String,Object,Object> operations = redisTemplate.opsForHash();
        operations.put("哈希","name","陈明");
        operations.put("哈希","name","陈明2号");
        String value = (String) operations.get("哈希", "name");
        System.out.println("hash name"+value);
    }
    
    //list集合
    @Test
    public void t5(){
        ListOperations<String,String> list = redisTemplate.opsForList();
        list.leftPush("list","桃尻香茗芽");
        list.leftPush("list","夕美诗");
        list.leftPush("list","三上悠亚");
        String value = list.leftPop("n list");
        System.out.println(value.toString());
    }

    @Test
    public void testGetUser() {
        SysUser user = sysUserService.getUser("bdqn");
        System.out.println(user);

        SysUser user1 = sysUserService.getUser("bdqn");
        System.out.println(user1);
    }


}
