package com.atracker.core.enums;

public enum Stored {
	REDIS("redis"),DB("db"),FILE("file");
	private String media;
	Stored(String i){
		this.media=i;
	}
	
	@Override
	public String toString() { 
		return  this.media;
	}
}
