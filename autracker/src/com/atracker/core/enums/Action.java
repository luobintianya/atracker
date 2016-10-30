package com.atracker.core.enums;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
 
public class Action extends EnumValueAbs  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ConcurrentMap<String, Action> cache = new ConcurrentHashMap<String, Action>(); 
	
	public static final Action ADD = valueOf("add");
	public static final Action REMOVE = valueOf("remove");
	public static final Action VIEW = valueOf("view");
	public static final Action CLICK = valueOf("click");
	public static final Action MOVE = valueOf("move");
	public static final Action UNKNOWE = valueOf("unknowe"); 
	public Action(final String _code) {

		super(_code);
	
	}; 
	public static Action valueOf(final String code)
	{
		final String key = code.toLowerCase();
		Action result = cache.get(key);
		if (result == null)
		{
			Action newValue = new Action(code);
			Action previous = cache.putIfAbsent(key, newValue);
			result = previous != null ? previous : newValue;
		}
		return result; 
		
	} 
	
}