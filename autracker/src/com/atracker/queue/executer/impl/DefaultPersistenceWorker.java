package com.atracker.queue.executer.impl;

import com.atracker.queue.executer.PersistenceWorker;
import com.atracker.threads.AtrackerThreadPool;

public class DefaultPersistenceWorker extends PersistenceWorker {

	public DefaultPersistenceWorker(AtrackerThreadPool poolservice, String name, int id) {
		super(poolservice, name, id); 
	}

}
