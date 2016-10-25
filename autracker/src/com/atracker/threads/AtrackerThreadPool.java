package com.atracker.threads;

import java.util.concurrent.CountDownLatch;

import com.atracker.data.TrackerInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.executer.AtrackerWorkerThread;
import com.atracker.queue.executer.PersistenceWorker;
import com.atracker.queue.executer.impl.DefaultPersistenceWorker;
import com.atracker.service.AtrackerQueueService;

/**
 * this a thread group , we use  threads get data from queue and
 *  save data to persistence service.
 * 
 * 
 * @author Robin
 *
 */
public abstract class AtrackerThreadPool {

	private volatile AtrackerWorkerThread[] workerThreads;
	private boolean ignoreError;
	private boolean hasErrors;
	private boolean finished;
 
	private final CountDownLatch workersEndedSignal;
	private WorkerValueQueue<TrackerInfo> queue;
	private final int maxWorkers;
	public AtrackerThreadPool(int maxWorkers){
		queue=  AtrackerQueueService.getQueueInstance(maxWorkers);
		this.maxWorkers=maxWorkers;  
		this.ignoreError=true;
		this.workersEndedSignal=new CountDownLatch(maxWorkers); 
		ensureHasWorkerThread();
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

	private final WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo> doWhilePut = new WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo>()
	{ 
		@Override
		public boolean execute(final WorkerValueQueue<TrackerInfo> paramWorkerValueQueue, final TrackerInfo paramE)
		{
			return (isAllWorkerDead() == false);// if have one is alive then true otherwise if false
		}
	};

 

	private final WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo> doWhileWait = new WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo>()
	{
		@Override
		public boolean execute(final WorkerValueQueue<TrackerInfo> paramWorkerValueQueue, final TrackerInfo info)
		{ 
			if (isAllWorkerDead() != false)
			{
				hasErrors = true; 
				return false;
			} 
			return true;
		}
	};


	private final boolean isAllWorkerDead()// all worker is die?
	{
		for (int i = 0; i < this.maxWorkers; ++i)
		{
			if (this.workerThreads[i].isAlive())
			{
				return false;
			}
		}
		return true;
	}
	 
	public void equeue(TrackerInfo info) {
		
		if (this.finished)
		{
			throw new IllegalStateException("master is already finished - cannot enqueue " + info);
		}

		ensureHasWorkerThread();
		getQueue().put(info, this.doWhilePut);   
	} 
	 
	protected void waitForEmptyQueue()
	{
		getQueue().waitUntilEmpty(this.doWhileWait);
	} 
	
	protected PersistenceWorker createWorker( int threadID) {
		return new DefaultPersistenceWorker(this, "DefaultPersistenceWorker Worker <" + " " + (threadID + 1) + " of " + this.maxWorkers + ">", threadID);
	}
	
	public final TrackerInfo fetchNext(PersistenceWorker worker) throws InterruptedException {
		if ((this.finished) || ((!(this.hasErrors)) && (this.finished))) {
			return null;
		} 
		return ((TrackerInfo) getQueue().take(worker.getWorkNumber()));
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
	 * @return the queue
	 */
	public WorkerValueQueue<TrackerInfo> getQueue() {
		return queue;
	}
	
	public void clearWorkerNumber(final PersistenceWorker worker)
	{
		getQueue().clearValueTaken(worker.getWorkNumber());
	}
	
	public boolean notifyFinished(PersistenceWorker worker,TrackerInfo trackInfo) { 
		System.out.println(trackInfo.toString());  
	   getQueue().clearValueTaken(worker.getWorkNumber());
		return (!(this.finished));
	}
	/**
	 * @param queue the queue to set
	 */
	public void setQueue(WorkerValueQueue<TrackerInfo> queue) {
		this.queue = queue;
	}

}
