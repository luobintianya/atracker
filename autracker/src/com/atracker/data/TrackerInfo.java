package com.atracker.data;

import com.atracker.core.enums.ACTION;
import com.atracker.core.enums.LEVEL;
import com.atracker.core.enums.MODEL;


public class TrackerInfo { 
	
	public final String HOSTIP="hostIp";
	public  final String TRACKERIP="trackerIp";
	public  final String TRACKID="trackId";
	public  final String SPANID="spanId";
	public  final String PARENTID="parentId";
	public  final String METHODNAME="methodName";
	public  final String METHODFULLNAME="methodFullName";
	public  final String TIMESTAMP="timestamp";
	public  final String STARTTIME="starttime";
	public  final String ENDTIME="endtime";
	public  final String LOGINFO="logInfo";
	public  final String MODEL="model"; 
	private String hostIp;
	private String trackerIp; 
	private String trackId;
	private String parentTrackId;
	private LEVEL level;
	private MODEL model;
	private ACTION action;
	private int spanId;
	private int parentId;
	private String methodName;
	private String methodFullName	; 
	private long timestamp;
	private long starttime;
	private long endtime; 
	private TrackerCustomer dataBag;
	private int lineNumber;
 
  
	/**
	 * @return the spanId
	 */
	public int getSpanId() {
		return spanId;
	}
	/**
	 * @param spanId the spanId to set
	 */
	public void setSpanId(int spanId) {
		this.spanId = spanId;
	}
	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the methodFullName
	 */
	public String getMethodFullName() {
		return methodFullName;
	}
	/**
	 * @param methodFullName the methodFullName to set
	 */
	public void setMethodFullName(String methodFullName) {
		this.methodFullName = methodFullName;
	}
	 
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the starttime
	 */
	public long getStarttime() {
		return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	public long getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}
	/**
	 * @param hostIp the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	/**
	 * @return the trackerIp
	 */
	public String getTrackerIp() {
		return trackerIp;
	}
	/**
	 * @param trackerIp the trackerIp to set
	 */
	public void setTrackerIp(String trackerIp) {
		this.trackerIp = trackerIp;
	}
	/**
	 * @return the trackId
	 */
	public String getTrackId() {
		return trackId;
	}
	/**
	 * @param trackId the trackId to set
	 */
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AtrackerTrackerInfo [ parentTrackId="+parentTrackId+" hostIp=" + hostIp + ", dateBag=" + dataBag+", trackerIp=" + trackerIp
				+ ", model=" + model + ", Action=" + action + ", level=" + level+", trackId=" + trackId + ", spanId="
				+ spanId + ", parentId=" + parentId + ", methodName="
				+ methodName + ", methodFullName=" + methodFullName
				+ ", timestamp=" + timestamp + ", starttime=" + starttime
				+ ", endtime=" + endtime 
				+ ", lineNumber=" + lineNumber + "]";
	}
	/**
	 * @return the parentTrackId
	 */
	public String getParentTrackId() {
		return parentTrackId;
	}
	/**
	 * @param parentTrackId the parentTrackId to set
	 */
	public void setParentTrackId(String parentTrackId) {
		this.parentTrackId = parentTrackId;
	}
	/**
	 * @return the model
	 */
	public MODEL getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(MODEL model) {
		this.model = model;
	}
	/**
	 * @return the level
	 */
	public LEVEL getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(LEVEL level) {
		this.level = level;
	}
	/**
	 * @return the action
	 */
	public ACTION getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(ACTION action) {
		this.action = action;
	}
	/**
	 * @return the dateBag
	 */
	public TrackerCustomer getDateBag() {
		return dataBag;
	}
	/**
	 * @param dateBag the dateBag to set
	 */
	public void setDateBag(TrackerCustomer dataBag) {
		this.dataBag = dataBag;
	}
	
	
	

}
