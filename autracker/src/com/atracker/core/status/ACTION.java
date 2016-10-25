package com.atracker.core.status;


public enum ACTION{
	ADD("add"),REMOVE("remove"),VIEW("view"),UNKNOWE("unknowe");
	private String action;
	ACTION(String i){
		this.action=i;
	}
	
	@Override
	public String toString() { 
		return String.valueOf(this.action);
	}
	public String getACTION(){
		return this.action;
	}
	
}