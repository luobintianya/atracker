package com.atracker.core.impl;

import com.atracker.core.AtrackerContext;
import com.atracker.core.AtrackerMaster;
import com.atracker.core.enums.ACTION;
import com.atracker.core.enums.LEVEL;
import com.atracker.core.enums.MODEL;
import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.data.TrackerDataBag;
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

	public void trackerInfo(MODEL model,ACTION action,LEVEL level, TrackerDataBag info) {
		try {
			isEnable = System.getProperty(ATRACKENABLE) == null ? true
					: Boolean.valueOf(System.getProperty(ATRACKENABLE));
			if (isEnable) {
				AtrackerContext trackContext = getOrCreateAtrackerContextInternal();
				trackContext.setMaster(this);
				trackContext.setCurrentContext(Thread.currentThread()
						.getStackTrace());
				equeue(createAtrackerTrackerInfo( model, action,   level, info,  trackContext ));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ignore DefaultAtrackerMaster exception");
		}
	}
	

	private AtrackerContext getOrCreateAtrackerContextInternal(){
		return trackContext;
	}
	
	private AtrackerTrackerInfo createAtrackerTrackerInfo(MODEL model,ACTION action,LEVEL level,TrackerDataBag bag,AtrackerContext trackContext){
		AtrackerTrackerInfo value=new AtrackerTrackerInfo(); 
		if(LEVEL.START.equals(level)){ 
			value.setStarttime(System.currentTimeMillis());  
		}else if(LEVEL.END.equals(level)){
			value.setEndtime(System.currentTimeMillis()); 
		} 
		if(this.preAtrackerMaster!=null && preAtrackerMaster.getCurrentAtrackerContext()!=null){
			value.setParentTrackId(preAtrackerMaster.getCurrentAtrackerContext().getTrackerID());
		}
		value.setAction(action);
		value.setModel(model);
		value.setLevel(level);
		value.setTrackId(trackContext.getTrackerID());
		value.setSpanId(trackContext.getSpanID());
		value.setParentId(trackContext.getParentId());
		value.setMethodName(trackContext.getCurrentMethod().getMethodName());
		value.setLineNumber(trackContext.getCurrentMethod().getLineNumber());
		value.setMethodFullName(trackContext.getCurrentMethod().getClassName()+"."+trackContext.getCurrentMethod().getMethodName());
		value.setDateBag(bag); 
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

	@Override
	public void trackerInfo(MODEL model, ACTION action, TrackerDataBag info) {
	 trackerInfo(model, action,LEVEL.TRACK,info); 
	}

	@Override
	public void trackerInfo(MODEL model, TrackerDataBag info) {
		trackerInfo(model, ACTION.UNKNOWE, info);
		
	}

	@Override
	public void trackerInfo(TrackerDataBag info) {
	 trackerInfo(MODEL.DEFAULT, info);
		
	}

	 
}
