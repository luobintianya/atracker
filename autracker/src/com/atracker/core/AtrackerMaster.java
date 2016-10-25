package com.atracker.core;

import com.atracker.core.enums.ACTION;
import com.atracker.core.enums.LEVEL;
import com.atracker.core.enums.MODEL;


public interface AtrackerMaster {

 public void trackerInfo(MODEL model,ACTION action,LEVEL level,Object info);
 
 public void trackerInfo(MODEL model,ACTION action,Object info);
 
 public void trackerInfo(MODEL model,Object info);
 
 public void trackerInfo(Object info);
 
 
 public AtrackerContext getCurrentAtrackerContext();
   
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster(); 
}
