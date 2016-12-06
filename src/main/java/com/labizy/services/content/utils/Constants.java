package com.labizy.services.content.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	private static Logger logger = LoggerFactory.getLogger("com.labizy.services.content.AppLogger");

	private Map<String, String> defaultMap;
	public void setDefaultMap(Map<String, String> defaultMap) {
		this.defaultMap = defaultMap;
	}

	public static String DefaultValue;
	private static synchronized void setDefaultValue(String value){
		if(logger.isDebugEnabled()){
			logger.debug("Inside {} method", "Constants.setDefault()");
		}
		
		DefaultValue = value;
	}
	
	public static final String DEFAULT_CACHE_KEY = "HOMEPAGE_CONTENT_MODEL";
	public static final String DEFAULT_CACHE_KEY_TYPE = "DEFAULT";
}
