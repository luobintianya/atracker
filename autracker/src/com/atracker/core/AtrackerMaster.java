package com.atracker.core;

import com.atracker.core.enums.ACTION;
import com.atracker.core.enums.LEVEL;
import com.atracker.core.enums.MODEL;
import com.atracker.data.TrackerCustomer;


public interface AtrackerMaster {

 public void trackerInfo(MODEL model,ACTION action,LEVEL level,TrackerCustomer info);
 
 public void trackerInfo(MODEL model,ACTION action,TrackerCustomer info);
 
 public void trackerInfo(MODEL model,TrackerCustomer info);
 
 public void trackerInfo(TrackerCustomer info);
 
 
 public AtrackerContext getCurrentAtrackerContext();
   
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster(); 
}
