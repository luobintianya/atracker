package com.atracker.core.enums;


public enum ACTION{
	ADD("add"),REMOVE("remove"),VIEW("view"),CLICK("click"),MOVE("move"),UNKNOWE("unknowe");
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