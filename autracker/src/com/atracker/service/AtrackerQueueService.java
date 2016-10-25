package com.atracker.service;

import com.atracker.data.TrackerInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.impl.DefaultWorkerValueQueue;

/**
 * Queue service
 * @author Robin
 *
 */
public class AtrackerQueueService {

	 
	private static WorkerValueQueue<TrackerInfo>  queue=null  ; 
	private AtrackerQueueService(){  
	};
	
	public static synchronized WorkerValueQueue<TrackerInfo> getQueueInstance(int maxWorks) {
		
		if (queue == null) {
			queue = new DefaultWorkerValueQueue<TrackerInfo>(maxWorks);
		}
		return queue;
	} 
}
