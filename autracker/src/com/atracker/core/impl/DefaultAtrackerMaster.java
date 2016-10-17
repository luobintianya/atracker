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

	private ThreadLocal<AtrackerContext> currentLocal=new ThreadLocal<AtrackerContext>(); ; 
	private final int maxWorkers;
	private final CountDownLatch workersEndedSignal;
	private volatile AtrackerWorkerThread[] workerThreads;
	private  final WorkerValueQueue<AtrackerTrackerInfo> queue ;
	private AtrackerMaster preAtrackerMaster;
	private boolean ignoreError;
	private boolean hasErrors;
	private boolean finished;
	
	
	public DefaultAtrackerMaster(int maxWorkers){
		this.maxWorkers=maxWorkers;
		this.ignoreError=true;
		this.queue=new DefaultWorkerValueQueue<AtrackerTrackerInfo>(maxWorkers);  
		this.workersEndedSignal=new CountDownLatch(maxWorkers); 
 
		
	}
	public void trackerInfo(String title, String info,LEVEL level) {  
		AtrackerContext	trackContext =getOrCreateAtrackerContextInternal();
		trackContext.setMaster(this);
		trackContext.setCurrentContext(Thread.currentThread().getStackTrace());
		equeue(createAtrackerTrackerInfo(title,info,trackContext,level));
	}

	

	private AtrackerContext getOrCreateAtrackerContextInternal(){
	
		AtrackerContext trackContext;
		if(currentLocal.get()==null){ 
			System.out.println("has not get Context");
			System.out.println("----------"+currentLocal+"-------------"); 
			trackContext=new DefaultAtrackerContext();
			currentLocal.set(trackContext);
		}else{
			System.out.println("has get Context");
			trackContext=currentLocal.get();
		}
		return trackContext;
	}
	
	private AtrackerTrackerInfo createAtrackerTrackerInfo(String titile,String info,AtrackerContext trackContext,LEVEL level){
		AtrackerTrackerInfo value=new AtrackerTrackerInfo();  
 
		if(LEVEL.START.equals(level)){

			value.setStarttime(System.currentTimeMillis()); 
			
		}else if(LEVEL.END.equals(level)){
			value.setEndtime(System.currentTimeMillis());
			
		}
		value.setTrackId(Thread.currentThread().getName()+trackContext.getTrackerID());
		value.setSpanId(trackContext.getSpanID());
		value.setParentId(trackContext.getParentId());
		value.setMethodName(trackContext.getCurrentMethod().getMethodName());
		value.setLineNumber(trackContext.getCurrentMethod().getLineNumber());
		value.setMethodFullName(trackContext.getCurrentMethod().getClassName()+"."+trackContext.getCurrentMethod().getMethodName());
		value.setLogInfo(info); 
		return value;
	}


	private final WorkerValueQueue.ExecuteWhileWaiting<AtrackerTrackerInfo> doWhilePut = new WorkerValueQueue.ExecuteWhileWaiting<AtrackerTrackerInfo>()
	{ 
		@Override
		public boolean execute(final WorkerValueQueue<AtrackerTrackerInfo> paramWorkerValueQueue, final AtrackerTrackerInfo paramE)
		{
			return (isAllWorkerDead() == false);// if have one is alive then true otherwise if false
		}
	};

 

	private final WorkerValueQueue.ExecuteWhileWaiting<AtrackerTrackerInfo> doWhileWait = new WorkerValueQueue.ExecuteWhileWaiting<AtrackerTrackerInfo>()
	{
		@Override
		public boolean execute(final WorkerValueQueue<AtrackerTrackerInfo> paramWorkerValueQueue, final AtrackerTrackerInfo info)
		{ 
			if (isAllWorkerDead() != false)
			{
				hasErrors = true; 
				return false;
			} 
			return true;
		}
	};

	
	 
	protected void waitForEmptyQueue()
	{
		getQueue().waitUntilEmpty(this.doWhileWait);
	}

	
	
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
	 
	public void equeue(AtrackerTrackerInfo info) {
		
		if (this.finished)
		{
			throw new IllegalStateException("master is already finished - cannot enqueue " + info);
		}

		ensureHasWorkerThread();
		getQueue().put(info, this.doWhilePut); 
 
			
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
					//ExecuterPool.getExcuteService().submit(threads[i]);
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
	
	public void clearWorkerNumber(final PersistenceWorker worker)
	{
		getQueue().clearValueTaken(worker.getWorkNumber());
	}
	
	public boolean notifyFinished(PersistenceWorker worker,AtrackerTrackerInfo trackInfo) { 
		System.out.println(trackInfo.getTrackId()); 
		System.out.println(trackInfo.getMethodFullName());
		System.out.println(trackInfo.getLogInfo());
		getQueue().clearValueTaken(worker.getWorkNumber()); 
		return (!(this.finished));
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
	 * @return the currentLocal
	 */
	public ThreadLocal<AtrackerContext> getCurrentLocal() {
		return currentLocal;
	}
	 
	public enum LEVEL{
		START(1),TRACK(2),END(3);
		private int level;
		LEVEL(int i){
			this.level=i;
		}
		
		@Override
		public String toString() { 
			return String.valueOf(this.level);
		}
		public int getLevel(){
			return this.level;
		}
		
	}

	@Override
	public AtrackerContext getCurrentAtrackerContext() {
		return getOrCreateAtrackerContextInternal();
		
	}
	@Override
	public void setPreAtrackerMaster(AtrackerMaster pre) {
		this.preAtrackerMaster=pre;
	}
	/**
	 * @return the preAtrackerMaster
	 */
	public AtrackerMaster getPreAtrackerMaster() {
		return preAtrackerMaster;
	}

	 
}
