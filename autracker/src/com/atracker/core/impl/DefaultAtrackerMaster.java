package com.atracker.core.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import com.atracker.core.AtrackerContext;
import com.atracker.core.AtrackerMaster;
import com.atracker.core.enums.Action;
import com.atracker.core.enums.Level;
import com.atracker.core.enums.Model;
import com.atracker.core.enums.ext.ExtAction;
import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;
import com.atracker.service.AtrackerThreadPoolService;
import com.atracker.utils.ObjectToT;
 
 

public class DefaultAtrackerMaster  implements AtrackerMaster{

	private ThreadLocal<AtrackerContext> currentLocal=new ThreadLocal<AtrackerContext>(); 
	private final String ATRACKENABLE="atrack.enable"; 
	private volatile AtrackerMaster preAtrackerMaster=null; 
	private	volatile AtrackerContext trackContext ; 
	private boolean isEnable=true;
	
	public DefaultAtrackerMaster(){
		this.isEnable=true; 
		this.trackContext=new DefaultAtrackerContext();
		
		
	}

	 
	public void trackerInfo(Model model,Action action,Level level, Object info) {
		try {
			isEnable = System.getProperty(ATRACKENABLE) == null ? true //enable or not
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
	
	private TrackerInfo<BaseInfo> createAtrackerTrackerInfo(Model model,Action action,Level level, Object bag,AtrackerContext trackContext){
		TrackerInfo<BaseInfo> value=new TrackerInfo<> (); 
		if(Level.START.equals(level)){ 
			value.setStarttime(System.currentTimeMillis());  
		}else if(Level.END.equals(level)){
			value.setEndtime(System.currentTimeMillis()); 
		} 
		if(this.preAtrackerMaster!=null && preAtrackerMaster.getCurrentAtrackerContext()!=null){
			value.setParentTrackId(preAtrackerMaster.getCurrentAtrackerContext().getTrackerID());
		}
		try {
			value.setHostIp(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		value.setAction(action);
		value.setModel(model);
		value.setLevel(level);
		value.setTimestamp(new Date().getTime());
		value.setTrackId(trackContext.getTrackerID());
		value.setSpanId(trackContext.getSpanID());
		value.setParentId(trackContext.getParentId());
		value.setMethodName(trackContext.getCurrentMethod().getMethodName());
		value.setLineNumber(trackContext.getCurrentMethod().getLineNumber());
		value.setMethodFullName(trackContext.getCurrentMethod().getClassName()+"."+trackContext.getCurrentMethod().getMethodName());
		value.setDateBag(ObjectToT.convertObjToCartT(bag,model)); 
		return value;
	}
	
 
	  
	 
	public void equeue(TrackerInfo<? extends BaseInfo> info) { 
		 
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
	public void trackerInfo(Model model, Action action, Object info) {
	 trackerInfo(model, action,Level.TRACK,info ); 
	}

	@Override
	public void trackerInfo(Model model, Object info) {
	 trackerInfo(model, Action.UNKNOWE,Level.TRACK,info );
		
	}

	@Override
	public void trackerInfo(Object info) {
		trackerInfo(Model.DEFAULT, ExtAction.UNKNOWE,Level.TRACK,info );
		
	}

 
	
	
	

	 
}
