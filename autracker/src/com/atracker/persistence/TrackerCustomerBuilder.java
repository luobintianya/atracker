package com.atracker.persistence;

import java.util.Map;

import com.atracker.persistence.data.StoreTrackerData;
import com.atracker.persistence.data.impl.StoreMapData;
import com.atracker.persistence.data.impl.StoreStringData;

public class TrackerCustomerBuilder {

	private TrackerCustomerBuilder(){ 
	};
	
	public static String getString(Object customerData){
		StoreTrackerData<String> customer=new StoreStringData();
		return customer.getContent(customerData);
	}

	public static Map<String, String> getMap(Object customerData) {
		StoreTrackerData<Map<String, String>> customer = new StoreMapData();
		return customer.getContent(customerData);
	}
	
}
