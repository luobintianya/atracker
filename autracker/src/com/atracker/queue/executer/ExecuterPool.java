package com.atracker.queue.executer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuterPool {
	
	private final static int maxThreadSize= 10;
	
	public static ExecutorService getExcuteService(){ 
		return Executors.newFixedThreadPool(maxThreadSize);
	}

}
