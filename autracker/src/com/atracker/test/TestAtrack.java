package com.atracker.test;

import com.atracker.core.Atracker;
import com.atracker.core.status.LEVEL;

public class TestAtrack {

	public static void main(String[] args) {
	a(); 
	}

	public static void a(){ 
	 	Atracker.currentAtrackerMaster().trackerInfo("a", "aaaaa", LEVEL.START);;
	 	b();
	}
	public static void b(){ 
	 	Atracker.currentAtrackerMaster().trackerInfo("b", "bbbbb", LEVEL.TRACK);;
	 	Thread t1=new Thread(new Runnable() { //for test thread situation
			@Override
			public void run() {
				 
				e();

			
			}
		});
	 	Thread t2=new Thread(new Runnable() { //for test thread situation
			@Override
			public void run() {
				e();
				 Thread t3=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						e();
						f();
					}
				});
				 t3.start();
			}
		});
	 	t1.start(); 
	 	
		System.out.println("--------------------------------------------------");
		 
	 	t2.start(); 
	  
	 	g();
	 	
	}
	public static void e(){
		System.out.println("**********e******************\n");
	 	
		Atracker.currentAtrackerMaster().trackerInfo("e","-------"+"eeee", LEVEL.TRACK);;
		f();
	}
	public static void f(){
		System.out.println("***********f*****************\n"); 
		Atracker.currentAtrackerMaster().trackerInfo("f","-------"+"ffffff", LEVEL.TRACK);;
		
	}
	
	public static void g(){
		System.out.println("***********g*****************\n"); 
		Atracker.currentAtrackerMaster().trackerInfo("g","-------"+"ggggggg", LEVEL.TRACK);;
		
	}
}
