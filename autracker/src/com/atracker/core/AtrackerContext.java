package com.atracker.core;

import java.util.Map;

public abstract class AtrackerContext {

	 
	protected AtrackerMaster master=null; 
	
	protected Map<String,Object> parameters=null;

	/**
	 * @return the master
	 */
	public AtrackerMaster getMaster() {
		return master;
	}

	/**
	 * @param master the master to set
	 */
	public void setMaster(AtrackerMaster master) {
		this.master = master;
	}
	

}
