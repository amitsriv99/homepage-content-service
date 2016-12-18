package com.labizy.services.content.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labizy.services.content.beans.ContentModelBean;
import com.labizy.services.content.exceptions.ContentModelNotFoundException;
import com.labizy.services.content.exceptions.ContentProcessingException;

public class CacheFactory {
	private static Logger logger = LoggerFactory.getLogger("com.labizy.services.content.AppLogger");
	
	private long maxAge;
	private Map<String, CacheFactory.CacheObject> cacheStore;
	
	private CommonUtils commonUtils;
	
	public void setCommonUtils(CommonUtils commonUtils) {
		this.commonUtils = commonUtils;
	}

	public CacheFactory(int cacheMaxAgeInMinutes) {
		if(logger.isInfoEnabled()){
			logger.info("Inside CacheFactory c'tor");
		}

		if((cacheMaxAgeInMinutes <= 0) || (cacheMaxAgeInMinutes > 60)){
			cacheMaxAgeInMinutes = 1000 * 60 * 60;
		}
		
		if(logger.isInfoEnabled()){
			logger.info("The max age of cached objects is set to " + cacheMaxAgeInMinutes + " minutes");
		}

		this.maxAge = 1000 * 60 * cacheMaxAgeInMinutes;
		this.cacheStore = new WeakHashMap<String, CacheFactory.CacheObject>();
	}

	public ContentModelBean getCachedObject(String cacheKey, String cacheKeyType) 
									throws ContentModelNotFoundException, ContentProcessingException{
		if(logger.isDebugEnabled()){
			logger.debug("Inside {}", "CacheFactory.getCachedObject(String)");
		}
		
		ContentModelBean contentModelBean = null;
		CacheObject cacheObject = null;
		
		if(! StringUtils.isEmpty(cacheKey)){
			cacheObject = cacheStore.get(cacheKey);
			
			if(cacheObject == null){
				if(logger.isInfoEnabled()){
					logger.info("Cache store was empty for cache key : {}", cacheKey);
				}
	
				synchronized(this){
					cacheObject = cacheStore.get(cacheKey);
					if(cacheObject == null){
						contentModelBean = getHomepageContentModelBean(cacheKeyType);
						
						cacheObject = new CacheObject(contentModelBean);
						cacheStore.put(cacheKey, cacheObject);
					}
				}
			} else if((System.currentTimeMillis() - cacheObject.birthTimestamp) > this.maxAge){
				if(logger.isInfoEnabled()){
					logger.info("Cache store has expired the cache key : {}", cacheKey);
				}
	
				synchronized(this){
					cacheObject = cacheStore.get(cacheKey);
					if((System.currentTimeMillis() - cacheObject.birthTimestamp) > this.maxAge){
						cacheStore.remove(cacheKey);
	
						contentModelBean = getHomepageContentModelBean(cacheKeyType); 
						cacheObject = new CacheObject(contentModelBean);
						
						cacheStore.put(cacheKey, cacheObject);
					}
				}
			} 
			else{
				if(logger.isInfoEnabled()){
					logger.info("Cache store has a valid item for cache key : {}", cacheKey);
				}
	
				contentModelBean = cacheObject.contentModelBean;
			}
		}else{
			contentModelBean = getHomepageContentModelBean(cacheKeyType);
			
			cacheObject = new CacheObject(contentModelBean);
			cacheStore.put(cacheKey, cacheObject);
		}
		
		return contentModelBean;
	}

	private ContentModelBean getHomepageContentModelBean(String cacheKeyType) 
								throws ContentModelNotFoundException, ContentProcessingException{
		
		if(logger.isDebugEnabled()){
			logger.debug("Inside {}", "CacheFactory.getHomepageContentModelBean(String)");
		}

		ContentModelBean contentModelBean = null;

		String contentRepository = System.getProperty("homepage.content.repository");
		File afile = new File(contentRepository + File.separator + "homepage-contentmodel.json");
		
		if(! afile.exists()){
			throw new ContentModelNotFoundException(afile.getAbsolutePath() + " not found..");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			contentModelBean = mapper.readValue(afile, ContentModelBean.class);
		} catch (JsonParseException e) {
			throw new ContentProcessingException(e);
		} catch (JsonMappingException e) {
			throw new ContentProcessingException(e);
		} catch (IOException e) {
			throw new ContentProcessingException(e);
		}
		
		if(contentModelBean == null){
			throw new ContentProcessingException("Failed to build the content model from " + afile.getAbsolutePath());
		}

		return contentModelBean;
	}
	
    private class CacheObject{
		private long birthTimestamp;
		private ContentModelBean contentModelBean;
		
		public CacheObject(ContentModelBean contentModelBean){
			this.contentModelBean = contentModelBean;
			this.birthTimestamp = System.currentTimeMillis();
		}
	}
}