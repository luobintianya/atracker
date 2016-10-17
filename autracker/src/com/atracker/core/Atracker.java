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
	private static AtrackerMaster currentMaster; 

	private Atracker() {
	};

	public static AtrackerMaster currentAtrackerMaster() {
		AtrackerMaster temp = localMaster.get();
		if (currentMaster == null) {
			if (temp == null) {
				System.out.println("************new Master**************");
				temp = factory.getInstance();
				localMaster.set(temp);
			}
			currentMaster = localMaster.get();
		} else if (currentMaster != null && temp == null) { 
			temp = factory.getInstance();
			temp.setPreAtrackerMaster(currentMaster);
			currentMaster = temp;
		}
		return currentMaster;
	}

}
