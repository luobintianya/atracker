package com.atracker.persistence.redis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.atracker.data.TrackerInfo;
import com.atracker.persistence.PersistenceStrategy;

public class RedisPersistenceStrategy implements PersistenceStrategy{
	private static final String redisHost = "192.168.0.100";
	private static final int port = 6379;

	private final static Jedis jedis = new Jedis(redisHost, port);

	@Override
	public boolean saveToSomePlace(TrackerInfo info) {

		Map<String, String> infoMap = new HashMap<String, String>();  
		infoMap.put(info.HOSTIP, info.getHostIp()); 
		infoMap.put(info.MODEL,info.getModel().getMODEL());
		infoMap.put(info.ACTION, info.getAction().getACTION());  
		infoMap.put(info.LEVEL, info.getLevel().getLevel()); 
		infoMap.put(info.TRACKID, info.getTrackId()); 
		infoMap.put(info.METHODFULLNAME, info.getMethodFullName()); 
		infoMap.put(info.TIMESTAMP, String.valueOf(info.getTimestamp())); 
		jedis.hmset(info.getModel().getMODEL(),infoMap);  
		return true;
	}

}
