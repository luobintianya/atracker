package com.atracker.persistence.data;

import com.atracker.data.tracking.BaseInfo;

/**
 * @author Robin
 *
 * @param <T> is target structure in store(db,redis,hbase eg)
 * @param <S>  baseInfo depend on which kind of data need to be store 
 */
public abstract class TransformDataAbs<T,S extends BaseInfo> implements TransformData<T,S >{

	protected abstract  T convertInternal(S obj)  ;
	
	@Override
	public T getContent(S obj) { 
		return convertInternal(obj);
	}
}
