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
	protected static AtrackerFactory<AtrackerMaster> factory = new DefaultAtrackerFactory();

	private static final ThreadLocal<AtrackerMaster> localMaster = new ThreadLocal<AtrackerMaster>();
	private static AtrackerMaster currentMaster ; 

	private Atracker() {
	};

	public static AtrackerMaster currentAtrackerMaster() { 
		AtrackerMaster temp = localMaster.get();   
		//synchronized (localMaster) {
			
	
		if (currentMaster == null) { 
			if (temp == null) { 
				temp = factory.getInstance();
				localMaster.set(temp);
			}
			currentMaster = localMaster.get();
			return currentMaster;
		} else if (currentMaster != null && temp == null) {  
			AtrackerMaster	newMaster = factory.getInstance();
			newMaster.setPreAtrackerMaster(currentMaster);  
			currentMaster = newMaster;
			localMaster.set(newMaster);  
			return newMaster;
		} else{
			currentMaster=temp;
			return temp;
		}
		//}
		  
	}

}
