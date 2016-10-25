package com.atracker.persistence;

import com.atracker.persistence.data.TrackerStored;
import com.atracker.persistence.data.impl.StringStored;

public class TrackerCustomerBuilder {

	private TrackerCustomerBuilder(){ 
	};
	
	public static String getString(Object customerData){
		TrackerStored<String> customer=new StringStored();
		return customer.getContent(customerData);
	}
	
}
