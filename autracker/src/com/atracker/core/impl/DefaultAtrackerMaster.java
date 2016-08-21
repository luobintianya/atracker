package com.atracker.core.impl;

import java.util.concurrent.CountDownLatch;
 

import com.atracker.core.AtrackerContext;
import com.atracker.core.AtrackerMaster;
import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.executer.AtrackerWorkerThread;
import com.atracker.queue.executer.PersistenceWorker;
import com.atracker.queue.executer.impl.DefaultPersistenceWorker;
import com.atracker.queue.impl.DefaultWorkerValueQueue;
 

public class DefaultAtrackerMaster implements AtrackerMaster {

	private AtrackerContext context;
	private final int maxWorkers;
	private final CountDownLatch workersEndedSignal;
	private volatile AtrackerWorkerThread[] workerThreads;
	private  final WorkerValueQueue<AtrackerTrackerInfo> queue ;
	private boolean ignoreError;
	private boolean hasErrors;
	private boolean finished;
	
	
	public DefaultAtrackerMaster(int maxWorkers){
		this.maxWorkers=maxWorkers;
		this.ignoreError=true;
		this.queue=new DefaultWorkerValueQueue<AtrackerTrackerInfo>(maxWorkers); 
		this.workersEndedSignal=new CountDownLatch(maxWorkers); 
	}
	public void trackerInfo(String title, String info) { 
		
		
	}

	public void setCurrentAtrackerContext(AtrackerContext context) { 
		
	}

	public void equeue(AtrackerTrackerInfo info) {
 
			
	}

	
	protected void ensureHasWorkerThread(){
		if (this.workerThreads != null)
		{
			return;
		}
		CountDownLatch workersStartSignal = null;
		synchronized (this)
		{
			if (this.workerThreads == null)
			{
				workersStartSignal = new CountDownLatch(1);
				final AtrackerWorkerThread[] threads = new AtrackerWorkerThread[this.maxWorkers];
				for (int i = 0; i < this.maxWorkers; ++i)
				{
					threads[i] = new AtrackerWorkerThread(createWorker(i), workersStartSignal, this.workersEndedSignal);

					threads[i].start();
				}
				this.workerThreads = threads;
			}
		}
		if (workersStartSignal == null)
		{
			return;
		}
		workersStartSignal.countDown();
	}
	
	
	public final AtrackerTrackerInfo fetchNext(PersistenceWorker worker) throws InterruptedException {
		if ((this.finished) || ((!(this.hasErrors)) && (this.finished))) {
			return null;
		}

		return ((AtrackerTrackerInfo) getQueue().take(worker.getWorkNumber()));
	}

	protected PersistenceWorker createWorker(int threadID) {
		return new DefaultPersistenceWorker(this, "DefaultPersistenceWorker Worker <" + " " + (threadID + 1) + " of " + this.maxWorkers + ">", threadID);
	}
	/**
	 * @return the queue
	 */
	public WorkerValueQueue<AtrackerTrackerInfo> getQueue() {
		return queue;
	}
	/**
	 * @return the ignoreError
	 */
	public boolean isIgnoreError() {
		return ignoreError;
	}
	/**
	 * @param ignoreError the ignoreError to set
	 */
	public void setIgnoreError(boolean ignoreError) {
		this.ignoreError = ignoreError;
	}
	/**
	 * @return the context
	 */
	public AtrackerContext getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(AtrackerContext context) {
		this.context = context;
	}
}
