package com.atracker.service;

import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.impl.DefaultWorkerValueQueue;

/**
 * Queue service
 * @author Robin
 *
 */
public class AtrackerQueueService {

	 
	private static WorkerValueQueue<TrackerInfo<?extends BaseInfo>>  queue=null  ; 
	private AtrackerQueueService(){  
	};
	
	public static synchronized WorkerValueQueue<TrackerInfo<?extends BaseInfo>> getQueueInstance(int maxWorks) {
		
		if (queue == null) {
			queue = new DefaultWorkerValueQueue<TrackerInfo<?extends BaseInfo>>(maxWorks);
		}
		return queue;
	} 
}
