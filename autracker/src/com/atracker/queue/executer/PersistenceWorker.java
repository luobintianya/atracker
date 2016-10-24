package com.atracker.queue.executer;

import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.persistence.PersistenceProvider;
import com.atracker.service.PersistenceService;
import com.atracker.threads.AtrackerThreadPool;

public abstract class PersistenceWorker implements Runnable{

	private AtrackerThreadPool poolService; 
	private String name;
	private int workNumber;
	private AtrackerTrackerInfo currentItem;
	
	private PersistenceService persistenceService;
	
	public PersistenceWorker(AtrackerThreadPool poolService, String name,int id){
		this.poolService=poolService;
		this.name=name;
		this.workNumber=id; 
		this.persistenceService=PersistenceProvider.getPersistenceService(); 
	}
	public void run() {  
		
		
		try { 
			for(setCurrentItem(getPoolService().fetchNext(this)); getCurrentItem() != null; setCurrentItem(getPoolService().fetchNext(this))){ //get current item
				startRecord(); //start record.  
			}
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			getPoolService().clearWorkerNumber(this);
		}

	}
	
	protected void startRecord() {
		try {
			getPersistenceService().persistence(getCurrentItem());
		} finally {
			getPoolService().notifyFinished(this, getCurrentItem());
		}
	}

 
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the workNumber
	 */
	public int getWorkNumber() {
		return workNumber;
	}
	/**
	 * @param workNumber the workNumber to set
	 */
	public void setWorkNumber(int workNumber) {
		this.workNumber = workNumber;
	}
	/**
	 * @return the currentItem
	 */
	public AtrackerTrackerInfo getCurrentItem() {
		return currentItem;
	}
	/**
	 * @param currentItem the currentItem to set
	 */
	protected void setCurrentItem(AtrackerTrackerInfo currentItem) {
		this.currentItem = currentItem;
	}
	/**
	 * @return the persistenceService
	 */
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	/**
	 * @param persistenceService the persistenceService to set
	 */
	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}
	/**
	 * @return the poolService
	 */
	public AtrackerThreadPool getPoolService() {
		return poolService;
	}
	/**
	 * @param poolService the poolService to set
	 */
	public void setPoolService(AtrackerThreadPool poolService) {
		this.poolService = poolService;
	}

}
