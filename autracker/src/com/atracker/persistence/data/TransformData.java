package com.atracker.persistence.data;


/**
 * store data bag used transform customer data
 * 
 * @author Robin
 *
 */
public abstract interface TransformData<T> {
	
	   T  getContent(Object obj);   
}
