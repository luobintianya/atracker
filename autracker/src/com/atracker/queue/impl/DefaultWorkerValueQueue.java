package com.atracker.queue.impl;

import java.util.AbstractList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.atracker.queue.AbstractWorkerValueQueue;

/**
 * this class user for store MessageWriter
 * 
 * @author Robin
 * 
 * 
 */
public class DefaultWorkerValueQueue<E> extends AbstractWorkerValueQueue<E> {

	public static final int DEFAULT_PUT_INTERVAL = 500;
	public static final TimeUnit DEFAULT_PUT_INTERVAL_UNIT;
	private final int workerSize;//同时允许多少个线程操作这个queue
	private final int maxQueueSize;
	private final ReadWriteLock valueQueueLock;
	private final Condition valueQueueEmpty; //if queue is empty then waiting  
	private final Condition valueQueueFull;
	private final Queue<E> queue;//store the object for consumer
	private volatile boolean stopped;
	private final AtomicReferenceArray<E> takenValues;
	private final DefaultWorkerValueQueue.TakenValueListAdapter<E> takenValuesListForExec;

	static {
		DEFAULT_PUT_INTERVAL_UNIT = TimeUnit.MILLISECONDS;
	}

	public DefaultWorkerValueQueue(int workerSize, int maxQueueSize) {
		this.stopped = false; 
		this.workerSize = workerSize;
		this.maxQueueSize = maxQueueSize;
		this.queue = new ConcurrentLinkedQueue<E>();//store object
		this.takenValues = new AtomicReferenceArray<E>(workerSize); //record take user info and take object
		this.takenValuesListForExec = new DefaultWorkerValueQueue.TakenValueListAdapter<E>( this.takenValues); 
		this.valueQueueLock = new ReentrantReadWriteLock();
		this.valueQueueEmpty = this.valueQueueLock.writeLock().newCondition(); //if queue is empty then waiting if have someone put element then  signal
		this.valueQueueFull = this.valueQueueLock.writeLock().newCondition();// if queue is full then waiting if take by someone then signal 
	}

	public DefaultWorkerValueQueue(int workerSize) {
		this(workerSize, -1);
	}

	public void stop() {
		this.valueQueueLock.writeLock().lock();

		try {
			this.stopped = true;

		} finally {
			this.valueQueueEmpty.signalAll();
			this.valueQueueLock.writeLock().unlock();
		}
	}

	private static class TakenValueListAdapter<E> extends AbstractList<E> {
		private final AtomicReferenceArray<E> references;

		TakenValueListAdapter(AtomicReferenceArray<E> references) {
			this.references = references;
		}

		public E get(int index) {
			return this.references.get(index);
		}

		public int size() {
			return this.references.length();
		}
	}

	public E executeOnTakenValues(ExecuteOnTaken<E> exec) {
		if (exec == null) {
			throw new IllegalArgumentException("exec was null");
		} else {
			this.valueQueueLock.readLock().lock();

			E value;
			try {
				value = exec.execute(this, this.takenValuesListForExec);// call back function take Element and excute other method

			} finally {
				this.valueQueueLock.readLock().unlock();
			}

			return value;
		}
	}

	public E take(int workerNumber) {
		E ret = null;
		if (!this.stopped) {

			this.valueQueueLock.writeLock().lock();

			try {
				while (!this.stopped && (ret = this.queue.poll()) == null) {

					this.valueQueueEmpty.await();
				}
				if (ret != null) {

					this.setValueTakenBy(workerNumber, ret);

				}
			} catch (InterruptedException arg5) {
				;
			} finally {
				this.valueQueueFull.signalAll();
				this.valueQueueLock.writeLock().unlock();
			}
		} 
		return ret;
	}

	public void clearValueTaken(int workerNumber) {
		this.valueQueueLock.writeLock().lock();

		try {
			this.setValueTakenBy(workerNumber, null); 
		} finally {
			this.valueQueueFull.signalAll();
			this.valueQueueLock.writeLock().unlock();
		}
	}

	public boolean put(E value, ExecuteWhileWaiting<E> exec) {
		
		this.valueQueueLock.writeLock().lock(); 
		try {
			if (this.stopped) {

				throw new IllegalStateException("queue " + this
						+ " is already stopped");
			}
			while (this.maxQueueSize > 0
					&& this.queue.size() >= this.maxQueueSize) {

				if (!this.execute(exec, value)) {

					return false;

				}

				try {
					this.valueQueueFull.await(500L, DEFAULT_PUT_INTERVAL_UNIT);

				} catch (InterruptedException arg5) {
				 
				}
			}

			this.queue.add(value);

			this.valueQueueEmpty.signalAll();

		} finally {
			this.valueQueueLock.writeLock().unlock();

		}
		return true;
	}

	public void waitUntilEmpty(long time, TimeUnit timeUnit,
			ExecuteWhileWaiting<E> exec) {
		do {
			if (!this.waitIfNotEmpty(time, timeUnit)) {
			}
		} while (this.execute(exec, null));

	} 
	private boolean waitIfNotEmpty(long time, TimeUnit timeUnit) {
		this.valueQueueLock.writeLock().lock();
		boolean keepWaiting;
		try {
			keepWaiting = this.isValueTakenOrQueueNotEmptyInternal();
			if (keepWaiting) { 
				this.valueQueueFull.await(time, timeUnit);
				keepWaiting = this.isValueTakenOrQueueNotEmptyInternal();

			}
		} catch (InterruptedException e) {
			keepWaiting = false;

		} finally {
			this.valueQueueLock.writeLock().unlock();
		}
		return keepWaiting;
	}

	public void clear() {
		this.valueQueueLock.writeLock().lock();

		try {
			this.queue.clear();
			for (int i = 0; i < this.workerSize; ++i) {

				this.takenValues.set(i, null);

			}
		} finally {
			this.valueQueueFull.signalAll();
			this.valueQueueLock.writeLock().unlock();
		}
	}

	private boolean execute(ExecuteWhileWaiting<E> exec, E value) {
		boolean ret = true;
		if (exec != null) {

			ret = exec.execute(this, value);
		}
		return ret;
	}

	public boolean isValueTakenOrQueueNotEmpty() {
		this.valueQueueLock.readLock().lock();

		boolean arg1;

		try {
			arg1 = this.isValueTakenOrQueueNotEmptyInternal();

		} finally {
			this.valueQueueLock.readLock().unlock();
		}
		return arg1;
	}

	private boolean isValueTakenOrQueueNotEmptyInternal() {
		return !this.queue.isEmpty() || this.isValueTaken();
	}

	private boolean isValueTaken() {
		for (int i = 0; i < this.workerSize; ++i) {

			if (this.isValueTakenBy(i)) {

				return true;
			}
		}

		return false;
	}

	private boolean isValueTakenBy(int workerNumber) {
		return this.takenValues.get(workerNumber) != null;
	}

	private void setValueTakenBy(int workerNumber, E value) {
		if (value == null) {

			this.takenValues.set(workerNumber, null);

		} else if (!this.takenValues.compareAndSet(workerNumber, null, value)) {

			throw new IllegalStateException(
					"there is already a taken value for worker " + workerNumber);
		}
	}
}
