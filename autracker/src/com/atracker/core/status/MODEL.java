package com.atracker.core.status;

public enum MODEL{
	CART("cart"),ACCOUNT("account"),ORDER("order"),BANNER("banner"),PRODUCT("product"),SEARCH("search"),WISH("wish");
	private String model;
	MODEL(String i){
		this.model=i;
	}
	
	@Override
	public String toString() { 
		return String.valueOf(this.model);
	}
	public String getMODEL(){
		return this.model;
	}
	
}

