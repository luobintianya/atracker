package com.atracker.persistence.data.impl;

import com.atracker.persistence.data.TransformData;

public class TransfromToStringData  implements TransformData<String>{  
	
	@Override
	public String getContent(Object obj) { 
		return obj.toString();
	}

}
