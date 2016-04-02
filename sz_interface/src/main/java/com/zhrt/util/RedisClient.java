package com.zhrt.util;

import java.util.List;

import com.system.util.property.PropertiesConfigDynamic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;


/**
 * redis工具类
 * @author tian_ln
 * @version 1.3
 * @since 1.3
 * @date 2015年12月24日 上午10:18:08
 */
public class RedisClient {
	
	private static JedisPool pool = null;  
	public static final int DATABASE_NO = 0;
    
    /** 
     * 构建redis连接池 
     *  
     * @param ip 
     * @param port 
     * @return JedisPool 
     */  
    public static JedisPool getPool() {  
        if (pool == null) {  
           JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
             config.setMaxTotal(500);
            //config.setMaxActive(500);  
           // config.setMaxIdle(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
            config.setMaxIdle(8);  
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
            config.setMaxWaitMillis(-1);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
            config.setTestOnBorrow(true);  
            //pool = new JedisPool(config,PropertiesConfigDynamic.getConfig("redisUrl"), 6379);  
            int timeout=60000;
            pool = new JedisPool(config, PropertiesConfigDynamic.getConfig("redisUrl"), 6379, timeout, PropertiesConfigDynamic.getConfig("redisPwd"));
           // pool = new JedisPool("", 6379);
        }  
        return pool;  
    }  
      
    /** 
     * 返还到连接池 
     *  
     * @param pool  
     * @param redis 
     */  
    public static void returnResource(JedisPool pool, Jedis redis) {  
        if (redis != null) {  
            pool.returnResource(redis);  
           
        }  
    }  
    
    /**
     * 对集合进行迭代查询
     * @author tian_ln
     * @version 1.3.1.0
     * @since 1.3.1.0
     * @param jedis
     * @param key 进行排序的集合的key
     * @param match 进行模糊查询的匹配符
     * @param count 在每次迭代中应该从数据集里返回多少元素，然后进行match匹配
     * @return
     */
    public static List<String> sscan(Jedis jedis,String key,String match,int count){
    	ScanParams  scan = new ScanParams();
		scan.match("*"+match+"*");
		scan.count(count);
		List<String> sscanList = jedis.sscan(key, 0, scan).getResult();
    	return sscanList;
    }
      
    /** 
     * 获取数据 
     *  
     * @param key 
     * @return 
     */  
    public static String get(String key){  
        String value = null;  
          
        JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            pool = getPool();  
            jedis = pool.getResource();  
            value = jedis.get(key);  
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
            e.printStackTrace();  
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
          
        return value;  
    }  
    
    public static void main(String[] args) {
    	JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            pool = getPool();  
            jedis = pool.getResource();  
         
        	Double f = jedis.zscore("appseq", "3");
            if(jedis.zscore("appseq", "3")!=null){
            
                System.out.println(f);
            }else{
            	System.out.println("w");
            }
            
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            //返还到连接池  
            //returnResource(pool, jedis);  
        }  
	}

}
