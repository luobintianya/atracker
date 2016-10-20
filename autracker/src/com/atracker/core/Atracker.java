package com.atracker.core;

import com.atracker.core.impl.DefaultAtrackerFactory;

/**
 * for get current Atrackermaster
 * 
 * @author Robin
 * 
 * 
 * 
 */
public class Atracker {
	private static final AtrackerFactory factory = new DefaultAtrackerFactory();

	private static final ThreadLocal<AtrackerMaster> localMaster = new ThreadLocal<AtrackerMaster>();
	private static AtrackerMaster currentMaster =null; 

	private Atracker() {
	};

	public static AtrackerMaster currentAtrackerMaster() {

		AtrackerMaster temp = localMaster.get();   
		synchronized (localMaster) { 
		if (currentMaster == null) { 
			if (temp == null) { 
				temp = factory.getInstance();
				localMaster.set(temp);
			}
			currentMaster = localMaster.get();
		} else if (currentMaster != null && temp == null) {  
			AtrackerMaster	newMaster = factory.getInstance();
			newMaster.setPreAtrackerMaster(currentMaster); 
			System.out.println(currentMaster.getCurrentAtrackerContext().getTrackerID()+" "+Thread.currentThread()+"老线程" +currentMaster);
			currentMaster = newMaster;
			localMaster.set(newMaster); 
			System.out.println(currentMaster.getCurrentAtrackerContext().getTrackerID()+" "+Thread.currentThread()+"新开线程 temp" +newMaster);  
		} else{
			currentMaster=temp;
		}
		}
		return currentMaster;
	}

}
