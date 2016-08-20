package com.atracker.queue;

import java.util.concurrent.TimeUnit;
 
 

public abstract class AbstractWorkerValueQueue<E> implements WorkerValueQueue<E> {
   public static final int DEFAULT_WAIT_INTERVAL = 500;
   public static final TimeUnit DEFAULT_WAIT_INTERVAL_UNIT = TimeUnit.MILLISECONDS;
	 
	 
	   public void put(E value)
	   {
	     put(value, null);
	   }
	 
	 
	   public void waitUntilEmpty()
	   {
	     waitUntilEmpty(null);
	   }
	 
	 
	   public void waitUntilEmpty(WorkerValueQueue.ExecuteWhileWaiting<E> exec)
	   {
		   waitUntilEmpty(500L, DEFAULT_WAIT_INTERVAL_UNIT, exec);
	   }
	 }
