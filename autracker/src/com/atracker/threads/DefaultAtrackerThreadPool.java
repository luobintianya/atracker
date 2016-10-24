package com.atracker.threads;

public class DefaultAtrackerThreadPool  extends AtrackerThreadPool{

	private final static int maxtWorkders=5;
	
	public DefaultAtrackerThreadPool(){
		this(maxtWorkders);
	}
	
	
	public DefaultAtrackerThreadPool(int maxWorkers) {
		super(maxWorkers); 
	}

}
