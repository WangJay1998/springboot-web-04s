package com.ktjiaoyu.demo2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//连接池
public class RedisPool {
    //服务器IP
    private static String ADDR="127.0.0.1";
    //端口号
    private static int port=6379;
    //可用连接实例的最大数目,默认值为8
    private static int MAX_ACTIVE=1024;

    //控制一个pool最多有多少个状态为idle(空闲)的jedis 默认值8
    private static int MAX_IDLE=200;

    //等待可用连接的最大时间,单位毫秒，-1，永不超时，超过等待时间，直接抛出JedisConnectionExectpion
    private static int MAX_WAIT=10000;
    private static int TIMEOUT=10000;

    private static boolean TEST_ON_BORROW=true;
    private static JedisPool jedisPool=null;

    static {
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        jedisPool=new JedisPool(config,ADDR,port,TIMEOUT);
    }

    /**
     * 获取jedis实例
     */
    public synchronized static Jedis getJedis(){
        if (jedisPool!=null){
            Jedis resource=jedisPool.getResource();
            return resource;
        }else{
            return null;
        }
    }

    //释放jedis资源
    public static void returnResource(final Jedis jedis){
        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
    }
}
