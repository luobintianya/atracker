package com.atracker.test;

import com.atracker.core.Atracker;
import com.atracker.core.enums.Action;
import com.atracker.core.enums.Model;

public class TestAtrackT2 {

	public static void main(String[] args) {
		TestAtrackT2 t2=new TestAtrackT2();
		t2.a();
		
	}

	public   void a(){ 
	 	Atracker.currentAtrackerMaster().trackerInfo( "aaaaa" );;
	 	e();
	} 
	public   void e(){
		System.out.println("**********e******************\n");
	 	
		Atracker.currentAtrackerMaster().trackerInfo(Model.CATEGORY,Action.CLICK,"wines");;
		f();
	}
	public   void f(){
		System.out.println("***********f*****************\n"); 
		Atracker.currentAtrackerMaster().trackerInfo("-------"+"ffffff");;
		
	}
	
	public   void g(){
		System.out.println("***********g*****************\n"); 
		Atracker.currentAtrackerMaster().trackerInfo("-------"+"ggggggg");;
		
	}
}
