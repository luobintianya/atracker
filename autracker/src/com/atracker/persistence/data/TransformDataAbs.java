package com.atracker.persistence.data;

public abstract class TransformDataAbs<T> implements TransformData<T>{

	protected abstract  T convertInternal(Object obj)  ;
	
	@Override
	public T getContent(Object obj) { 
		return convertInternal(obj);
	}
}
