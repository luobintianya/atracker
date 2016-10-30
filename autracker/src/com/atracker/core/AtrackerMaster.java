package com.atracker.core;

import com.atracker.core.enums.Action;
import com.atracker.core.enums.Level;
import com.atracker.core.enums.Model;


public interface AtrackerMaster {

 public void trackerInfo(Model model,Action action,Level level,Object info );
 
 public void trackerInfo(Model model,Action action,Object info);
 
 public void trackerInfo(Model model,Object info);
 
 public void trackerInfo(Object info);
 
 
 public AtrackerContext getCurrentAtrackerContext();
   
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster(); 
}
