package com.atracker.persistence;

import java.util.HashMap;
import java.util.Map;

import com.atracker.data.TrackerInfo;
import com.atracker.data.tracking.BaseInfo;
import com.atracker.persistence.data.TransformData;
import com.atracker.persistence.data.impl.TransformToMapData;
import com.atracker.persistence.data.impl.TransformToStringData;

public class TrackerCustomerBuilder {

	private TrackerCustomerBuilder(){ 
	};
	
	public static String getString(TrackerInfo<? extends BaseInfo> customerData){
		TransformData<String,BaseInfo> customer=new TransformToStringData();
		return customer.getContent(customerData.getDateBag());
	}

	public static Map<String, String> getMap(TrackerInfo<? extends BaseInfo> info) {
		TransformData<Map<String, String>,BaseInfo> customer = new TransformToMapData();
		Map<String, String> infoMap = new HashMap<String, String>();   
		infoMap.put(info.HOSTIP, info.getHostIp()); 
		infoMap.put(info.MODEL,info.getModel().getCode());
		infoMap.put(info.ACTION, info.getAction().getCode());  
		infoMap.put(info.LEVEL, info.getLevel().getCode()); 
		infoMap.put(info.TRACKID, info.getTrackId()); 
		infoMap.put(info.METHODFULLNAME, info.getMethodFullName()); 
		infoMap.put(info.TIMESTAMP, String.valueOf(info.getTimestamp())); 
		infoMap.put(info.MID,info.getMid());  
		 infoMap.putAll(customer.getContent(info.getDateBag()));
		 return infoMap;
	}
	
}
