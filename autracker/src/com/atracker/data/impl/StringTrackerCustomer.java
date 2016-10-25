package com.atracker.data.impl;

import com.atracker.data.TrackerCustomer;

public class StringTrackerCustomer  implements TrackerCustomer<String>{ 
	@Override
	public String getContent(Object obj) { 
		return obj.toString();
	}

}
