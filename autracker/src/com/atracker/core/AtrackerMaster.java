package com.atracker.core;

import com.atracker.core.enums.ACTION;
import com.atracker.core.enums.LEVEL;
import com.atracker.core.enums.MODEL;
import com.atracker.data.TrackerDataBag;


public interface AtrackerMaster {

 public void trackerInfo(MODEL model,ACTION action,LEVEL level,TrackerDataBag info);
 
 public void trackerInfo(MODEL model,ACTION action,TrackerDataBag info);
 
 public void trackerInfo(MODEL model,TrackerDataBag info);
 
 public void trackerInfo(TrackerDataBag info);
 
 
 public AtrackerContext getCurrentAtrackerContext();
   
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster(); 
}
