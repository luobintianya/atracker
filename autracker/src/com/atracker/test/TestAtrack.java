package com.atracker.test;

import com.atracker.core.Atracker; 
import com.atracker.core.impl.DefaultAtrackerMaster.LEVEL;

public class TestAtrack {

	public static void main(String[] args) {
	a();
		
	}

	public static void a(){
	 	Atracker.currentAtrackerMaster().trackerInfo("a", "kkkk", LEVEL.START);; 
	}
}
