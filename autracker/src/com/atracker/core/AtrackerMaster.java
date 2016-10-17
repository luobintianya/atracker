package com.atracker.core;

import com.atracker.core.impl.DefaultAtrackerMaster.LEVEL;
import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.queue.executer.PersistenceWorker;


public interface AtrackerMaster {

 public void trackerInfo(String title,String info,LEVEL level);
 
 public AtrackerContext getCurrentAtrackerContext();
 
 public AtrackerTrackerInfo fetchNext(PersistenceWorker worker)  throws InterruptedException;
  
 void equeue(AtrackerTrackerInfo info);
 
 void setPreAtrackerMaster(AtrackerMaster pre);
 
 AtrackerMaster getPreAtrackerMaster();
 
 public void clearWorkerNumber(final PersistenceWorker worker);
 
 boolean notifyFinished(PersistenceWorker worker,AtrackerTrackerInfo trackInfo) ;
}
