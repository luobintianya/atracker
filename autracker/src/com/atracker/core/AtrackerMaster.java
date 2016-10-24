package com.atracker.core;

import com.atracker.core.status.LEVEL;


public interface AtrackerMaster {

 public void trackerInfo(String title,String info,LEVEL level);
 
 public AtrackerContext getCurrentAtrackerContext();
  
 
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster(); 
}
