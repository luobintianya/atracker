package com.atracker.queue;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract interface WorkerValueQueue<E> {
	  public abstract Object executeOnTakenValues(ExecuteOnTaken<E> paramExecuteOnTaken);

	  public abstract E take(int paramInt);

	  public abstract void clearValueTaken(int paramInt);

	  public abstract void put(E paramE);

	  public abstract boolean put(E paramE, ExecuteWhileWaiting<E> paramExecuteWhileWaiting);

	  public abstract void waitUntilEmpty();

	  public abstract void waitUntilEmpty(ExecuteWhileWaiting<E> paramExecuteWhileWaiting);

	  public abstract void waitUntilEmpty(long paramLong, TimeUnit paramTimeUnit, ExecuteWhileWaiting<E> paramExecuteWhileWaiting);

	  public abstract void clear();

	  public abstract boolean isValueTakenOrQueueNotEmpty();

	  public abstract void stop();

	  public static abstract interface ExecuteOnTaken<E> //while  execute queue 
	  {
	    public abstract E execute(WorkerValueQueue<E> paramWorkerValueQueue, List<E> paramList);
	  }

	  public static abstract interface ExecuteWhileWaiting<E>
	  {
	    public abstract boolean execute(WorkerValueQueue<E> paramWorkerValueQueue, E paramE);
	  }


}
