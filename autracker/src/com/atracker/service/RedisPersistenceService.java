package com.atracker.service;

import com.atracker.data.TrackerInfo;
import com.atracker.persistence.PersistenceStrategy;
import com.atracker.persistence.redis.RedisPersistenceStrategy;

public class RedisPersistenceService implements PersistenceService {
	private PersistenceStrategy redisStore= new RedisPersistenceStrategy();

	public void persistence(TrackerInfo info) {
		
		redisStore.saveToSomePlace(info);
		
	}

}
