package com.atracker.test;

import com.atracker.core.Atracker;
import com.atracker.core.enums.Action;
import com.atracker.core.enums.Model;

public class TestAtrack {

	public static void main(String[] args) {
	a("main"); 
	}

	public static void a(String str){ 
	 	Atracker.currentAtrackerMaster().trackerInfo( Thread.currentThread()+"aaaaa" );;
	 	b("main");
	}
	public static void b(String str){ 
	 	Atracker.currentAtrackerMaster().trackerInfo(Thread.currentThread()+"bbbbb");;
	 	Thread t1=new Thread(new Runnable() { //for test thread situation
			@Override
			public void run() { 
				e("t1"); 
			
			}
		});
	 	Thread t2=new Thread(new Runnable() { //for test thread situation
			@Override
			public void run() {
				e("t2");
				 Thread t3=new Thread(new Runnable() { 
					@Override
					public void run() { 
						System.out.println("--------------------------------------------------"); 
						  e("t3");
						  f("t3");
						 System.out.println("--------------------------------------------------");
					} 
				});
						
					t3.start();
					
			}
		});
	 	t1.start(); 
	 	
		 
	 	t2.start(); 
	  
	 	g("main");
	 	
	}
	public static void e(String str){ 
	 	
		Atracker.currentAtrackerMaster().trackerInfo(Model.CATEGORY,Action.CLICK,Thread.currentThread()+"商品分类"+str);;
		f(str+" e-f");
	}
	public static void f(String str){ 
		Atracker.currentAtrackerMaster().trackerInfo(Thread.currentThread()+"我是f "+str);;
		
	}
	
	public static void g(String str){ 
		Atracker.currentAtrackerMaster().trackerInfo(Thread.currentThread()+"ggggggg"+str);;
		
	}
}
