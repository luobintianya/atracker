package com.atracker.persistence.data;

import com.atracker.data.tracking.BaseInfo;

public abstract class TransformDataAbs<T,S extends BaseInfo> implements TransformData<T,S >{

	protected abstract  T convertInternal(S obj)  ;
	
	@Override
	public T getContent(S obj) { 
		return convertInternal(obj);
	}
}
