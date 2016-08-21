package com.atracker.queue.executer.impl;

import com.atracker.core.AtrackerMaster;
import com.atracker.queue.executer.PersistenceWorker;

public class DefaultPersistenceWorker extends PersistenceWorker {

	public DefaultPersistenceWorker(AtrackerMaster master, String name, int id) {
		super(master, name, id); 
	}

}
