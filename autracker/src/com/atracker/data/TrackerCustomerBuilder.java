package com.atracker.data;

import com.atracker.data.impl.StringTracker;

public class TrackerCustomerBuilder {

	private TrackerCustomerBuilder(){ 
	};
	
	public static String getString(Object customerData){
		TrackerCustomer<String> customer=new StringTracker();
		return customer.getContent(customerData);
	}
	
}
