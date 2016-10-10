package com.atracker.queue.executer;

import com.atracker.core.AtrackerMaster;
import com.atracker.data.AtrackerTrackerInfo;
import com.atracker.persistence.PersistenceProvider;
import com.atracker.service.PersistenceService;

public abstract class PersistenceWorker implements Runnable{

	private AtrackerMaster master; 
	private String name;
	private int workNumber;
	private AtrackerTrackerInfo currentItem;
	
	private PersistenceService persistenceService;
	
	public PersistenceWorker(AtrackerMaster master, String name,int id){
		this.master=master;
		this.name=name;
		this.workNumber=id; 
		this.persistenceService=PersistenceProvider.getPersistenceService();
		
	}
	public void run() {  
		
		
		try { 
			for(setCurrentItem(getMaster().fetchNext(this)); getCurrentItem() != null; setCurrentItem(getMaster().fetchNext(this))){ //get current item
				startRecord(); //start record.

				System.out.println("xxxxxxxx"+getName()); 
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			getMaster().clearWorkerNumber(this);
		}

	}
	
	protected void startRecord() {
		try {
			getPersistenceService().persistence(getCurrentItem());
		} finally {
			getMaster().notifyFinished(this, getCurrentItem().getTrackId());
		}
	}

	/**
	 * @return the master
	 */
	public AtrackerMaster getMaster() {
		return master;
	}
	/**
	 * @param master the master to set
	 */
	public void setMaster(AtrackerMaster master) {
		this.master = master;
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

}
