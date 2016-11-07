package com.atracker.service;

import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;

public interface PersistenceService {

	public void persistence(TrackerInfo<? extends BaseInfo> info);
	
}
