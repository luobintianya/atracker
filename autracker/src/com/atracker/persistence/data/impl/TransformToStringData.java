package com.atracker.persistence.data.impl;

import com.atracker.data.tracking.BaseInfo;
import com.atracker.persistence.data.TransformDataAbs;

public class TransformToStringData  extends TransformDataAbs<String,BaseInfo> {   


	

	@Override
	protected String convertInternal(BaseInfo obj) { 
		return obj.getUserUId();
	}
	
		 
}
