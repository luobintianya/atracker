package com.atracker.queue.executer;

import java.util.concurrent.CountDownLatch;

public class AtrackerWorkerThread extends Thread {
	private final PersistenceWorker worker;
	private final CountDownLatch startSignal;
	private final CountDownLatch endSignal;

	public AtrackerWorkerThread(PersistenceWorker worker, CountDownLatch start,
			CountDownLatch end) {
		this.worker = worker;
		this.startSignal = start;
		this.endSignal = end;
		setPriority(Thread.MIN_PRIORITY);//low level priority
	}
	
}
