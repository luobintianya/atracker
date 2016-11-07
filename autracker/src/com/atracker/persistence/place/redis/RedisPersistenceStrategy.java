package com.atracker.persistence.place.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.atracker.data.TrackerInfo;
import com.atracker.persistence.PersistenceStrategy;
import com.atracker.persistence.TrackerCustomerBuilder;
import com.atracker.utils.PropertiesUtils;

/**
 * @author Robin
 *
 */
public class RedisPersistenceStrategy implements PersistenceStrategy{ 
  
	private  JedisPool jedisPool=null;
	
	public	RedisPersistenceStrategy(){ 
	 
			 
			Properties	props=	PropertiesUtils.loadProperties();
			JedisPoolConfig config = new JedisPoolConfig(); 
			config.setMaxIdle(Integer.valueOf(props
					.getProperty("jedis.pool.maxIdle"))); 
			config.setTestOnBorrow(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnBorrow"))); 
			config.setTestOnReturn(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnReturn"))); 
			jedisPool = new JedisPool(config, props.getProperty("redis.ip"),
					Integer.valueOf(props.getProperty("redis.port"))); 
		 
	 }
	@Override
	public boolean saveTrackerInfo(TrackerInfo info) { 
		 
		Map<String, String> infoMap = new HashMap<String, String>();  
		infoMap.put(info.HOSTIP, info.getHostIp()); 
		infoMap.put(info.MODEL,info.getModel().getCode());
		infoMap.put(info.ACTION, info.getAction().getCode());  
		infoMap.put(info.LEVEL, info.getLevel().getCode()); 
		infoMap.put(info.TRACKID, info.getTrackId()); 
		infoMap.put(info.METHODFULLNAME, info.getMethodFullName()); 
		infoMap.put(info.TIMESTAMP, String.valueOf(info.getTimestamp())); 
		infoMap.putAll(TrackerCustomerBuilder.getMap(info.getDateBag())); 
		infoMap.put(info.MID,info.getMid());
		Jedis jedis=jedisPool.getResource();
	    jedis.hmset(info.getTrackId()+"-"+info.getParentId()+"-"+info.getSpanId(),infoMap);    
		return true;
	}
  
}
