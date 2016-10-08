package com.atracker.core;

import java.util.Map;
/**
 * 
 * Process method stack
 * <pre>
 * -----------------------Frontend.Request----------------------
 * |				^			|								^
 * V				|     		|								| 
 * ----backend.call--    		|								| 
 *  parentId:1,spanId:2			V								| 
 *    							-----Backend.Dosomthing----------	
 *							   	 |	|	parenId:1		 ^	^
 *								 |	V	spanId:3		 |	| 
 *								 |	------Helper.Call-----	| 
 * 								 |    parentId:3			| 
 * 								 V 	  spanId:4				| 
 * 								 ------- Helper.call--------
 *									parentId:3 spanId:5	
 * 									
 * </pre>
 * 
 * 
 * @author Robin							
 *
 */
public   abstract  class AtrackerContext {

	 
	protected AtrackerMaster master = null;

	protected Map<String, Object> parameters = null;

	public abstract int getSpanID();

	public abstract int getParentId();

	public abstract String getTrackerID();

	public abstract StackTraceElement getCurrentMethod(); 
	
	public abstract String getCurrentMethodName();
	
	public abstract void setCurrentContext(StackTraceElement[] allMethod) ;
	/**
	 * @return the master
	 */
	public AtrackerMaster getMaster() {
		return master;
	}

	/**
	 * @param master the master to set
	 */
	public void setMaster(AtrackerMaster master) {
		this.master = master;
	}
	

}
