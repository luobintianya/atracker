package com.atracker.core;

import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.queue.executer.PersistenceWorker;


public interface AtrackerMaster {

 public void trackerInfo(String title,String info);
 
 public void setCurrentAtrackerContext(AtrackerContext context);
 
 public AtrackerTrackerInfo fetchNext(PersistenceWorker worker)  throws InterruptedException;
  
 void equeue(AtrackerTrackerInfo info);
 
}
