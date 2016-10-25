package com.atracker.persistence.data.impl;

import com.atracker.persistence.data.TrackerStored;

public class StringStored  implements TrackerStored<String>{  
	
	@Override
	public String getContent(Object obj) { 
		return obj.toString();
	}

}
