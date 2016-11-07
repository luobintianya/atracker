package com.atracker.persistence.place.redis;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;
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
			config.setBlockWhenExhausted(false);
			config.setMaxIdle(Integer.valueOf(props
					.getProperty("jedis.pool.maxIdle")));  
			config.setMaxTotal(Integer.valueOf(props
					.getProperty("jedis.pool.maxTotal")));
			config.setMaxWaitMillis(-1); 
			config.setTestOnBorrow(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnBorrow")));  
			jedisPool = new JedisPool(config, props.getProperty("redis.ip"),
					Integer.valueOf(props.getProperty("redis.port"))); 
		 
	 }
	@Override
	public boolean saveTrackerInfo(TrackerInfo<? extends BaseInfo> info) {  
	   
		Jedis jedis=jedisPool.getResource(); 
		jedis.hmset(info.getDateBag().toString(),TrackerCustomerBuilder.getMap(info));     
		jedis.close(); //need close !!
		return true;
	}
  
}
