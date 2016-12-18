package com.labizy.services.content;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.labizy.services.content.beans.ContentModelBean;
import com.labizy.services.content.beans.StatusBean;
import com.labizy.services.content.exceptions.ContentModelNotFoundException;
import com.labizy.services.content.utils.CacheFactory;
import com.labizy.services.content.utils.Constants;

@RestController
public class HomepageContentServiceController {
	private static Logger appLogger = LoggerFactory.getLogger("com.labizy.services.content.AppLogger");
	private static Logger traceLogger = LoggerFactory.getLogger("com.labizy.services.content.TraceLogger");
	
	private CacheFactory cacheFactory;

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST },headers="Accept=application/json")
	public @ResponseBody StatusBean get(final HttpServletResponse httpServletResponse){
		StatusBean status = new StatusBean();
		status.setStatusCode("" + HttpServletResponse.SC_FORBIDDEN);
		status.setStatusMessage("Directory listing is forbidden..!");
		
		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		return status;
	}
	
	@RequestMapping(value = "/_status", method = { RequestMethod.GET, RequestMethod.POST },headers="Accept=application/json")
	public @ResponseBody StatusBean getStatus(final HttpServletResponse httpServletResponse){
		StatusBean status = new StatusBean();
		status.setStatusCode("" + HttpServletResponse.SC_OK);
		status.setStatusMessage("Healthy..!");
		
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		
		return status;
	}
	
	@RequestMapping(value = "/homepage/v1/content-model", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ContentModelBean getContentModel(@RequestParam MultiValueMap<String, String> requestParams,
															@RequestHeader(value="X-OAUTH-TOKEN", required = false) String oauthToken,
																final HttpServletResponse httpServletResponse){
		if(appLogger.isDebugEnabled()){
			appLogger.debug("Inside {}", "HomepageContentServiceController.getContentModel()");
		}
		
		long startTimestamp = System.currentTimeMillis();
		
		ContentModelBean contentModelBean = null;
		String cacheKey = Constants.DEFAULT_CACHE_KEY;
		String cacheKeyType = Constants.DEFAULT_CACHE_KEY_TYPE;
		
		String errorCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		String errorDescription = "Unknown Exception. Please check the HomepageContentService application logs for further details.";

		if(StringUtils.isEmpty(oauthToken)){
			contentModelBean = new ContentModelBean();
			contentModelBean.setErrorCode("" + HttpServletResponse.SC_UNAUTHORIZED);
			contentModelBean.setErrorDescription("Unauthorized Access Exception. The AUTH token is not valid.");
			
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else{
			try {
				contentModelBean = cacheFactory.getCachedObject(cacheKey, cacheKeyType);
			} catch(ContentModelNotFoundException e){
				appLogger.error("Caught Exception {}", e);

				contentModelBean = new ContentModelBean();
				contentModelBean.setErrorCode(Integer.toString(HttpServletResponse.SC_NOT_FOUND));
				contentModelBean.setErrorDescription(e.getMessage());
			} catch (Exception e){
				appLogger.error("Caught Unknown Exception {}", e);
				errorDescription = errorDescription + "\n" + e.getMessage();
			} finally{
				if (contentModelBean == null){
					contentModelBean = new ContentModelBean();
					contentModelBean.setErrorCode(errorCode);
					contentModelBean.setErrorDescription(errorDescription);
					
					httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}else{
					if(StringUtils.isEmpty(contentModelBean.getErrorCode())){
						httpServletResponse.setStatus(HttpServletResponse.SC_OK);
					}else{
						httpServletResponse.setStatus(Integer.parseInt(contentModelBean.getErrorCode()));
					}
				}
			}
		}

		traceLogger.info("Inside HomepageContentServiceController.getContentModel(). Total Time Taken --> {} milliseconds", (System.currentTimeMillis() - startTimestamp));
		
		return contentModelBean;
	}

	public CacheFactory getCacheFactory() {
		return cacheFactory;
	}
	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}
}