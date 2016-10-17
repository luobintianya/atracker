package com.atracker.test;

import com.atracker.core.Atracker; 
import com.atracker.core.impl.DefaultAtrackerMaster.LEVEL;

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
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e();

				System.out.println("**********t1******************\n");
			 
			}
		});
	 	Thread t2=new Thread(new Runnable() { //for test thread situation
			@Override
			public void run() {
				e();
				 
			}
		});
	 	t1.start(); 
	 	t2.start(); 
	 	g();
	 	
	}
	public static void e(){
		System.out.println("**********e******************\n");
	 	
		Atracker.currentAtrackerMaster().trackerInfo("e", Thread.currentThread().getName()+"-------"+"eeee", LEVEL.TRACK);;
		f();
	}
	public static void f(){
		System.out.println("***********f*****************\n"); 
		Atracker.currentAtrackerMaster().trackerInfo("f", Thread.currentThread().getName()+"-------"+"ffffff", LEVEL.TRACK);;
		
	}
	
	public static void g(){
		Atracker.currentAtrackerMaster().trackerInfo("g", Thread.currentThread().getName()+"-------"+"ggggggg", LEVEL.TRACK);;
		
	}
}
