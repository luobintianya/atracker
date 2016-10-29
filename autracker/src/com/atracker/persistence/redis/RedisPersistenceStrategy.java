package com.atracker.persistence.redis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.atracker.data.TrackerInfo;
import com.atracker.persistence.PersistenceStrategy;
import com.atracker.persistence.TrackerCustomerBuilder;

public class RedisPersistenceStrategy implements PersistenceStrategy{ 
  
	private  JedisPool jedisPool=null;
	
	public	RedisPersistenceStrategy(){ 
		try { 
			Properties props = new Properties(); 
			props.load(RedisPersistenceStrategy.class.getClassLoader()
					.getResourceAsStream("redis.properties")); 
			JedisPoolConfig config = new JedisPoolConfig(); 
			config.setMaxIdle(Integer.valueOf(props
					.getProperty("jedis.pool.maxIdle"))); 
			config.setTestOnBorrow(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnBorrow"))); 
			config.setTestOnReturn(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnReturn"))); 
			jedisPool = new JedisPool(config, props.getProperty("redis.ip"),
					Integer.valueOf(props.getProperty("redis.port")));

		} catch (IOException e) {

			e.printStackTrace();

		}
	 }
	@Override
	public boolean saveTrackerInfo(TrackerInfo info) { 
		 
		Map<String, String> infoMap = new HashMap<String, String>();  
		infoMap.put(info.HOSTIP, info.getHostIp()); 
		infoMap.put(info.MODEL,info.getModel().getMODEL());
		infoMap.put(info.ACTION, info.getAction().getACTION());  
		infoMap.put(info.LEVEL, info.getLevel().getLevel()); 
		infoMap.put(info.TRACKID, info.getTrackId()); 
		infoMap.put(info.METHODFULLNAME, info.getMethodFullName()); 
		infoMap.put(info.TIMESTAMP, String.valueOf(info.getTimestamp())); 
		infoMap.putAll(TrackerCustomerBuilder.getMap(info.getDateBag())); 
		Jedis jedis=jedisPool.getResource();
	    jedis.hmset(info.getTrackId().hashCode()+"",infoMap);    
		return true;
	}

	public static void main(String[] args){
		
//			Properties prop= new Properties(); 
//			prop.load(RedisPersistenceStrategy.class.getResourceAsStream("redis.properties"));
//		  	JedisPoolConfig config = new JedisPoolConfig();
//	        config.setMaxActive(prop.getProperty("redis.maxActive"0));
//	        config.setMaxIdle(prop.getIntValue("redis.maxIdle", 10));
//	        //config.setMinIdle(10);
//	        config.setMaxWait(con.getLongValue("redis.maxWait", 1000L));
//	        config.setTestOnBorrow(true);
//	        config.setTestOnReturn(true);
//	        //config.testWhileIdle=true;
//	        //config.minEvictableIdleTimeMillis=30000;
//	        //config.timeBetweenEvictionRunsMillis=30000;
//	        
//	        pool = new JedisPool(config, redisaddr, redisport);
//		Jedis jedisx= new Jedis(redisHost, port);
//		jedisx.persist("ffffffffffff");
		
	}
}
