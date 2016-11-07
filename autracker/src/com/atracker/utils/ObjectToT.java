package com.atracker.utils;

import com.atracker.data.tracking.BaseInfo;
import com.atracker.data.tracking.CartBaseInfo;

public class ObjectToT  {
	
	public static BaseInfo convertObjToCartT(Object t){
		
		return new CartBaseInfo(t.toString());
	}

	 
}
