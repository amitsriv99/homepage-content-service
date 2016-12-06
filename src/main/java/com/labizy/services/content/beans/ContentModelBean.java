package com.labizy.services.content.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentModelBean {

	private ImageBean heroBanner;
	private MostPopularLabTestsBean mostPopularLabTests;
	private OurPartnerLabsBean ourPartnerLabs;
	private String errorCode;
	private String errorDescription;

	public ImageBean getHeroBanner() {
		return heroBanner;
	}
	public void setHeroBanner(ImageBean heroBanner) {
		this.heroBanner = heroBanner;
	}
	public MostPopularLabTestsBean getMostPopularLabTests() {
		return mostPopularLabTests;
	}
	public void setMostPopularLabTests(MostPopularLabTestsBean mostPopularLabTests) {
		this.mostPopularLabTests = mostPopularLabTests;
	}
	public OurPartnerLabsBean getOurPartnerLabs() {
		return ourPartnerLabs;
	}
	public void setOurPartnerLabs(OurPartnerLabsBean ourPartnerLabs) {
		this.ourPartnerLabs = ourPartnerLabs;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
