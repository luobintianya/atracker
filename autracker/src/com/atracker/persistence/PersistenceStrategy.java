package com.atracker.persistence;

import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;

/**
 * 
 *	Used to store info to some place</br>
 *  mix action or model to save data to some place<br>
 * .eg redis, file, db.
 *
 * @author Robin
 *
 */
public interface PersistenceStrategy {

	public boolean saveTrackerInfo(TrackerInfo<? extends BaseInfo> info);
	
	
}
