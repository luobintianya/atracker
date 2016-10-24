package com.atracker.service;

import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.impl.DefaultWorkerValueQueue;

/**
 * Queue service
 * @author Robin
 *
 */
public class AtrackerQueueService {

	 
	private static WorkerValueQueue<AtrackerTrackerInfo>  queue=null  ; 
	private AtrackerQueueService(){  
	};
	
	public static synchronized WorkerValueQueue<AtrackerTrackerInfo> getQueueInstance(int maxWorks) {
		
		if (queue == null) {
			queue = new DefaultWorkerValueQueue<AtrackerTrackerInfo>(maxWorks);
		}
		return queue;
	} 
}
