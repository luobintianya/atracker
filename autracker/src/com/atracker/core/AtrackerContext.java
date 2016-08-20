package com.atracker.core;

import java.util.Map;

public abstract class AtrackerContext {

	private static final ThreadLocal<AtrackerContext> atrackinfo=new ThreadLocal<AtrackerContext>();//store whole AtrackerContext
	
	protected AtrackerMaster master=null; 
	
	protected Map<String,Object> parameters=null;
	

}
