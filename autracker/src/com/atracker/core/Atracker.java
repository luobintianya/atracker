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
	private volatile static AtrackerMaster master = null;
	private static final ThreadLocal<AtrackerMaster> localMaster = new ThreadLocal<AtrackerMaster>();

	private Atracker() {
	};

	public static AtrackerMaster currentAtrackerMaster() {
		if ((master = localMaster.get()) == null) {
			if (master == null) {
				master = factory.getInstance();
				localMaster.set(master);
			}
		}
		return master;
	}

}
