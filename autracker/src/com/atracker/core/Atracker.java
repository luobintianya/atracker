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

	protected static final ThreadLocal<AtrackerMaster> localMaster = new ThreadLocal<AtrackerMaster>();
	private static AtrackerMaster currentMaster ; 

	protected Atracker() {
	};

	protected static AtrackerMaster createAtrackerMasterInternal() {
		return factory.getInstance();
	}
	public static AtrackerMaster currentAtrackerMaster() { 
		AtrackerMaster temp = localMaster.get();   
		//synchronized (localMaster) { 
		if (currentMaster == null) { 
			if (temp == null) { 
				temp = createAtrackerMasterInternal();
				localMaster.set(temp);
			}
			currentMaster = localMaster.get();
			return currentMaster;
		} else if (currentMaster != null && temp == null) {  
			AtrackerMaster	newMaster = createAtrackerMasterInternal();
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
