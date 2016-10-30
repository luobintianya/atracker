package com.atracker.persistence.data.impl;

import java.util.HashMap;
import java.util.Map;

import com.atracker.persistence.data.TransformDataAbs;

public class TransformToMapData extends TransformDataAbs<Map<String, String>> {

	@Override
	protected Map<String, String> convertInternal(Object obj) {
		Map<String,String> values=new HashMap<String, String>();
		values.put("traker", obj.toString());
		return values;
	}

	 

}
