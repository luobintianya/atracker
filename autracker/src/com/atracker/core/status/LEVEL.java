package com.atracker.core.status;


public enum LEVEL{
	START(1),TRACK(2),END(3);
	private int level;
	LEVEL(int i){
		this.level=i;
	}
	
	@Override
	public String toString() { 
		return String.valueOf(this.level);
	}
	public int getLevel(){
		return this.level;
	}
	
}


