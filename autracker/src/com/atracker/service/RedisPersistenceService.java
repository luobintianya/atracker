package com.atracker.service;

import com.atracker.data.TrackerInfo;
import com.atracker.persistence.PersistenceStrategy;

public class RedisPersistenceService implements PersistenceService {
	private PersistenceStrategy redisStore;

	public void persistence(TrackerInfo info) {
		
		redisStore.saveToSomePlace(info);
		
	}

}
