package com.atracker.core;

import com.atracker.core.status.ACTION;
import com.atracker.core.status.LEVEL;
import com.atracker.core.status.MODEL;


public interface AtrackerMaster {

 public void trackerInfo(MODEL model,ACTION action,LEVEL level,String info);
 
 public void trackerInfo(MODEL model,ACTION action,String info);
 
 public void trackerInfo(MODEL model,String info);
 
 public void trackerInfo(String info);
 
 
 public AtrackerContext getCurrentAtrackerContext();
   
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster(); 
}
