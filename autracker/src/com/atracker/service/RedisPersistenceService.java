package com.atracker.service;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.atracker.data.AtrackerTrackerInfo;

public class RedisPersistenceService implements PersistenceService {

	private static final String redisHost = "192.168.0.100";
	private static final int port = 6379;

	private final static Jedis jedis = new Jedis(redisHost, port);

	public void persistence(AtrackerTrackerInfo info) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(info.HOSTIP, info.getHostIp());
		map.put("age", "22");
		map.put("qq", "123456");
		//System.out.println(map);
		//jedis.hmset("user", map);
		
	 
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
//		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
//		System.out.println(rsmap);
//
//		// 删除map中的某个键值
//		jedis.hdel("user", "age");
//		System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是null
//		System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
//		System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
//		System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key
//		System.out.println(jedis.hvals("user"));// 返回map对象中的所有value

//		Iterator<String> iter = jedis.hkeys("user").iterator();
//		while (iter.hasNext()) {
//			String key = iter.next();
//			System.out.println(key + ":" + jedis.hmget("user", key));
//		}

	}

}
