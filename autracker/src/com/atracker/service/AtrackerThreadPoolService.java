package com.atracker.service;

import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.threads.AtrackerThreadPool;
import com.atracker.threads.DefaultAtrackerThreadPool;
import com.atracker.utils.AtrackerContants;


public class AtrackerThreadPoolService {
	
	private static AtrackerThreadPool threadPools;
	
	
	private AtrackerThreadPoolService(){ 
	}
	
	public synchronized static AtrackerThreadPool getAtrackPoolInstance() {

		if (threadPools == null) {
			threadPools = new DefaultAtrackerThreadPool(
					AtrackerContants.maxWorkers);
		}
		return threadPools;
	}

	public static void equeue(AtrackerTrackerInfo info) {
		if (threadPools == null) {
			threadPools = getAtrackPoolInstance();
		}

		threadPools.equeue(info);

	}
}
