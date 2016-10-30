package com.atracker.persistence.data.impl;

import com.atracker.persistence.data.TransformDataAbs;

public class TransformToStringData  extends TransformDataAbs<String> {   


	@Override
	protected String convertInternal(Object obj) {
		// TODO Auto-generated method stub
		return obj.toString();
	}
	
		 
}
