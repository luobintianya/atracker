package com.atracker.persistence.data.impl;

import java.util.HashMap;
import java.util.Map;

import com.atracker.persistence.data.StoreTrackerData;

public class StoreMapData implements StoreTrackerData<Map<String, String>> {

	@Override
	public Map<String, String> getContent(Object obj) {
		Map<String,String> values=new HashMap<String, String>();
		values.put("traker", obj.toString());
		return values;
	}

}
