package com.atracker.core.impl;

import com.atracker.core.AtrackerFactory;
import com.atracker.core.AtrackerMaster;

public class DefaultAtrackerFactory implements AtrackerFactory< AtrackerMaster> {  
	public  AtrackerMaster getInstance() {  
			return new DefaultAtrackerMaster(); 
	}
	 
}
