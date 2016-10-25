package com.atracker.data;


/**
 * store data bag used transform customer data
 * 
 * @author Robin
 *
 */
public abstract interface TrackerCustomer<T> {
	
	   T  getContent(Object obj);
 
	
}
