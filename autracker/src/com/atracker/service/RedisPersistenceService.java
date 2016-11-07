package com.atracker.service;

import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;
import com.atracker.persistence.PersistenceStrategy;
import com.atracker.persistence.place.redis.RedisPersistenceStrategy;

public class RedisPersistenceService implements PersistenceService {
	private PersistenceStrategy redisStore= new RedisPersistenceStrategy();

	public void persistence(TrackerInfo<? extends BaseInfo> info) {
			
		redisStore.saveTrackerInfo(info);
		
	}

}
