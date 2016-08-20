package com.atracker.core;

import com.atracker.data.AtrackerTrackerInfo;


public interface AtrackerMaster {

 public void trackerInfo(String title,String info);
 
 public void setCurrentAtrackerContext(AtrackerContext context);
 
  
 void equeue(AtrackerTrackerInfo info);
 
}
