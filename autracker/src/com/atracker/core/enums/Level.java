package com.atracker.core.enums;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Level extends EnumValueAbs {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9023251664142214609L;
	private static final ConcurrentMap<String, Level> cache = new ConcurrentHashMap<String, Level>(); 
	public static final Level START = valueOf("start");
	public static final Level END = valueOf("end");
	public static final Level TRACK = valueOf("track");  
	public Level(String _code){
		super(_code);
	} 
	
	public static Level valueOf(final String code)
	{
		final String key = code.toLowerCase();
		Level result = cache.get(key);
		if (result == null)
		{
			Level newValue = new Level(code);
			Level previous = cache.putIfAbsent(key, newValue);
			result = previous != null ? previous : newValue;
		}
		return result; 
		
	} 
}


