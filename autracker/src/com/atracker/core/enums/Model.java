package com.atracker.core.enums;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class  Model   extends EnumValueAbs{
 
	
	private static final long serialVersionUID = -9023251664142214609L;
	private static final ConcurrentMap<String, Model> cache = new ConcurrentHashMap<String, Model>(); 
	public static final Model CART = valueOf("cart");
	public static final Model ACCOUNT = valueOf("account"); 
	public static final Model CATEGORY = valueOf("category");
	public static final Model ORDER = valueOf("order");
	public static final Model BANNER = valueOf("product");  
	public static final Model PRODUCT = valueOf("banner");   
	public static final Model WISH = valueOf("wish");  
	public static final Model DEFAULT = valueOf("default");  
	public Model(String _code){
		super(_code);
	} 
	
	public static Model valueOf(final String code)
	{
		final String key = code.toLowerCase();
		Model result = cache.get(key);
		if (result == null)
		{
			Model newValue = new Model(code);
			Model previous = cache.putIfAbsent(key, newValue);
			result = previous != null ? previous : newValue;
		}
		return result; 
		
	} 
}

