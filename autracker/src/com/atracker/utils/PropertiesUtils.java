package com.atracker.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	private static final String fileName="atracker.properties";
	private static  Properties props =null; 

	public static Properties loadProperties() { 
		try {
			if (props == null) {
				props = new Properties();
				props.load(PropertiesUtils.class.getResourceAsStream(fileName));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}

}
