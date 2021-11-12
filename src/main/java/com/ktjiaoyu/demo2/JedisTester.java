package com.ktjiaoyu.demo2;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTester {
    private Jedis jedis;

    @Before    //初始化方法，对于每个方法都要执行一边
    public void setup(){
        //链接服务器
        jedis=new Jedis("127.0.0.1",6379);
        //权限认证,指定密码
//        jedis.auth("foobared");
    }

    @Test
    public void test(){
        jedis.set("name","陈明");
        System.out.println(jedis.get("name"));
        jedis.append("name","在拉屎不带纸");
        //删除name键
        jedis.del("name");
        System.out.println(jedis.get("name"));

        //设置多个键值对
        jedis.mset("name","陈明","age","48","sex","女");
        jedis.incrBy("age",2);
        System.out.println(jedis.get("name")+jedis.get("age")+jedis.get("sex"));
    }

}
