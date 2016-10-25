package com.atracker.core.enums;

public enum STORED {
	REDIS("redis"),DB("db"),FILE("file");
	private String media;
	STORED(String i){
		this.media=i;
	}
	
	@Override
	public String toString() { 
		return  this.media;
	}
}
