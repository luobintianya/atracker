package com.atracker.core.impl;

import com.atracker.core.AtrackerFactory;
import com.atracker.core.AtrackerMaster;

public class DefaultAtrackerFactory implements AtrackerFactory {

	private final static int MAXWORKERS=2;
	
	
	
	public  AtrackerMaster getInstance() { 
		return new DefaultAtrackerMaster(MAXWORKERS);
	}

}
