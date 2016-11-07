package com.atracker.data.tracking;

public abstract class BaseInfo {

	private String userUId;
	public BaseInfo(String userUid) { 
		this.userUId=userUid;
	}
	/**
	 * @return the userUId
	 */
	public String getUserUId() {
		return userUId;
	}
	/**
	 * @param userUId the userUId to set
	 */
	public void setUserUId(String userUId) {
		this.userUId = userUId;
	}
	 
}
