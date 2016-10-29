package com.atracker.persistence.data.impl;

import com.atracker.persistence.data.StoreTrackerData;

public class StoreStringData  implements StoreTrackerData<String>{  
	
	@Override
	public String getContent(Object obj) { 
		return obj.toString();
	}

}
