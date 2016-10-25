package com.atracker.core.enums;


public enum LEVEL{
	START("start"),END("end"),TRACK("track");
	private String level;
	LEVEL(String i){
		this.level=i;
	}
	
	@Override
	public String toString() { 
		return  this.level;
	}
	public String getLevel(){
		return this.level;
	}
	
}


