package com.atracker.core.impl;

import java.util.concurrent.CountDownLatch;

import com.atracker.core.AtrackerContext;
import com.atracker.core.AtrackerMaster;
import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.executer.AtrackerWorkerThread;
import com.atracker.queue.impl.DefaultWorkerValueQueue;

public class DefaultAtrackerMaster implements AtrackerMaster {

	private AtrackerContext context;
	private final int maxWorkers;
	private final CountDownLatch workersEndedSignal;
	private volatile AtrackerWorkerThread[] workerThreads;
	private  final WorkerValueQueue<AtrackerTrackerInfo> queue ;
	
	public DefaultAtrackerMaster(int maxWorkers){
		this.maxWorkers=maxWorkers;
		this.queue=new DefaultWorkerValueQueue<AtrackerTrackerInfo>(maxWorkers); 
		this.workersEndedSignal=new CountDownLatch(maxWorkers); 
	}
	public void trackerInfo(String title, String info) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentAtrackerContext(AtrackerContext context) {
		// TODO Auto-generated method stub
		
	}

	public void equeue(AtrackerTrackerInfo info) {
 
			
	}

	
	protected void ensureHasWorkerThread(){
		
		
		
		
	}
}
