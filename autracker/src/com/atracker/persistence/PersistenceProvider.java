package com.atracker.persistence;

import com.atracker.service.PersistenceService;
import com.atracker.service.RedisPersistenceService;

public class PersistenceProvider {

	private static PersistenceService persistenService;

	private PersistenceProvider() {
	};

	public static PersistenceService getPersistenceService() {
		if (persistenService == null) {
			persistenService = new RedisPersistenceService();
		}
		return persistenService;
	}

}
