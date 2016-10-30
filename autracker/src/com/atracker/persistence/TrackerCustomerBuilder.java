package com.atracker.persistence;

import java.util.Map;

import com.atracker.persistence.data.TransformData;
import com.atracker.persistence.data.impl.TransformToMapData;
import com.atracker.persistence.data.impl.TransformToStringData;

public class TrackerCustomerBuilder {

	private TrackerCustomerBuilder(){ 
	};
	
	public static String getString(Object customerData){
		TransformData<String> customer=new TransformToStringData();
		return customer.getContent(customerData);
	}

	public static Map<String, String> getMap(Object customerData) {
		TransformData<Map<String, String>> customer = new TransformToMapData();
		return customer.getContent(customerData);
	}
	
}
