package com.atracker.core.impl;

import java.util.concurrent.CountDownLatch;

import com.atracker.core.AtrackerContext;
import com.atracker.core.AtrackerMaster;
import com.atracker.core.status.LEVEL;
import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.queue.WorkerValueQueue;
import com.atracker.queue.executer.AtrackerWorkerThread;
import com.atracker.queue.executer.ExecuterPool;
import com.atracker.queue.executer.PersistenceWorker;
import com.atracker.queue.executer.impl.DefaultPersistenceWorker;
import com.atracker.queue.impl.DefaultWorkerValueQueue;
import com.atracker.service.AtrackerThreadPoolService;
 
 

public class DefaultAtrackerMaster implements AtrackerMaster {

	private ThreadLocal<AtrackerContext> currentLocal=new ThreadLocal<AtrackerContext>(); 
	private final String ATRACKENABLE="atrack.enable"; 
	private volatile AtrackerMaster preAtrackerMaster=null; 
	private	volatile AtrackerContext trackContext ;
	
	private boolean isEnable=true;
	
	public DefaultAtrackerMaster(){
		this.isEnable=true; 
		this.trackContext=new DefaultAtrackerContext();
		
		
	}
	public void trackerInfo(String title, String info,LEVEL level) {   
		isEnable=System.getProperty(ATRACKENABLE)==null?true:Boolean.valueOf(System.getProperty(ATRACKENABLE));  
		if(isEnable){
			AtrackerContext trackContext = getOrCreateAtrackerContextInternal();
			trackContext.setMaster(this);
			trackContext.setCurrentContext(Thread.currentThread().getStackTrace());
			equeue(createAtrackerTrackerInfo(title, info, trackContext, level));
		}
	}

	

	private AtrackerContext getOrCreateAtrackerContextInternal(){
		return trackContext;
	}
	
	private AtrackerTrackerInfo createAtrackerTrackerInfo(String titile,String info,AtrackerContext trackContext,LEVEL level){
		AtrackerTrackerInfo value=new AtrackerTrackerInfo(); 
		if(LEVEL.START.equals(level)){ 
			value.setStarttime(System.currentTimeMillis());  
		}else if(LEVEL.END.equals(level)){
			value.setEndtime(System.currentTimeMillis()); 
		} 
		if(this.preAtrackerMaster!=null && preAtrackerMaster.getCurrentAtrackerContext()!=null){
			value.setParentTrackId(preAtrackerMaster.getCurrentAtrackerContext().getTrackerID());
		}
		value.setTrackId(trackContext.getTrackerID());
		value.setSpanId(trackContext.getSpanID());
		value.setParentId(trackContext.getParentId());
		value.setMethodName(trackContext.getCurrentMethod().getMethodName());
		value.setLineNumber(trackContext.getCurrentMethod().getLineNumber());
		value.setMethodFullName(trackContext.getCurrentMethod().getClassName()+"."+trackContext.getCurrentMethod().getMethodName());
		value.setLogInfo(info); 
		return value;
	}
	
	 
	 
	public void equeue(AtrackerTrackerInfo info) { 
		AtrackerThreadPoolService.equeue(info);
	} 
	

	
	
	/**
	 * @return the currentLocal
	 */
	public ThreadLocal<AtrackerContext> getCurrentLocal() {
		return currentLocal;
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
