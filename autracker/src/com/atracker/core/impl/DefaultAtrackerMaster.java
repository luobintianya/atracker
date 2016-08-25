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
import com.atracker.service.BasicIdGeneratorService;
 
 

public class DefaultAtrackerMaster implements AtrackerMaster {

	private ThreadLocal<AtrackerTrackerInfo> currentLocal ;
	private AtrackerContext context;
	private final int maxWorkers;
	private final CountDownLatch workersEndedSignal;
	private volatile AtrackerWorkerThread[] workerThreads;
	private  final WorkerValueQueue<AtrackerTrackerInfo> queue ;
	private AtrackerContext trackContext=new DefaultAtrackerContext();
	private	AtrackerTrackerInfo value;
	private BasicIdGeneratorService generator=new BasicIdGeneratorService();
	private boolean ignoreError;
	private boolean hasErrors;
	private boolean finished;
	
	
	public DefaultAtrackerMaster(int maxWorkers){
		this.maxWorkers=maxWorkers;
		this.ignoreError=true;
		this.queue=new DefaultWorkerValueQueue<AtrackerTrackerInfo>(maxWorkers);  
		this.workersEndedSignal=new CountDownLatch(maxWorkers); 
		this.currentLocal=new ThreadLocal<AtrackerTrackerInfo>();
		value=new AtrackerTrackerInfo(); 
		generator=new BasicIdGeneratorService();
		this.currentLocal.set(value); 
		
	}
	public void trackerInfo(String title, String info,LEVEL level) {  
		
		trackContext.setMaster(this);
		trackContext.setCurrentContext(Thread.currentThread().getStackTrace());
		equeue(createAtrackerTrackerInfo(title,info,trackContext,level));
	}

	

	private AtrackerTrackerInfo createAtrackerTrackerInfo(String titile,String info,AtrackerContext trackContext,LEVEL level){
		if(LEVEL.START.equals(level)){
			value=new AtrackerTrackerInfo();  
			value.setTrackId(generator.generate());
			
		}else if(LEVEL.END.equals(level)){
			value.setEndtime(System.currentTimeMillis());
			value.setTrackId(generator.generate());
		}else{
			if(value==null){
				value=new AtrackerTrackerInfo();   
			}
		}
		value.setStarttime(System.currentTimeMillis()); 
		value.setMethodFullName(getContext().getCurrentMethod().getMethodName());
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
	@Override
	public void setCurrentAtrackerContext(AtrackerContext context) {
		this.trackContext=context;
	}
	/**
	 * @return the currentLocal
	 */
	public ThreadLocal<AtrackerTrackerInfo> getCurrentLocal() {
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
			// TODO Auto-generated method stub
			return String.valueOf(this.level);
		}
		public int getLevel(){
			return this.level;
		}
		
	}
}
