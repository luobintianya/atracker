package com.atracker.data;

import java.util.Date;

public class AtrackerTrackerInfo {
	
	
	public final String HOSTIP="hostIp";
	public  final String TRACKERIP="trackerIp";
	public  final String LEVEL="level";
	public  final String TRACKID="trackId";
	public  final String SPANID="spanId";
	public  final String PARENTID="parentId";
	public  final String METHODNAME="methodName";
	public  final String METHODFULLNAME="methodFullName";
	public  final String TIMESTAMP="timestamp";
	public  final String STARTTIME="starttime";
	public  final String ENDTIME="endtime";
	public  final String LOGINFO="logInfo";
	
	private String hostIp;
	private String trackerIp;
	private int level; 
	private int trackId;
	private int spanId;
	private int parentId;
	private String methodName;
	private String methodFullName	; 
	private long timestamp;
	private Date starttime;
	private Date endtime; 
	private Object logInfo;
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the trackId
	 */
	public int getTrackId() {
		return trackId;
	}
	/**
	 * @param trackId the trackId to set
	 */
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}
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
	 * @return the logInfo
	 */
	public Object getLogInfo() {
		return logInfo;
	}
	/**
	 * @param logInfo the logInfo to set
	 */
	public void setLogInfo(Object logInfo) {
		this.logInfo = logInfo;
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
	public Date getStarttime() {
		return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	public Date getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(Date endtime) {
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
	
	
	

}
