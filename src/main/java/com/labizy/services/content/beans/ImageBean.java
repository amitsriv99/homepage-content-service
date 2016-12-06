package com.labizy.services.content.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageBean {
	private String imageUrl;
	private String altText;
	private String actionButton;
	private String imageClick;
	private String redirectToPage;

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAltText() {
		return altText;
	}
	public void setAltText(String altText) {
		this.altText = altText;
	}
	public String getActionButton() {
		return actionButton;
	}
	public void setActionButton(String actionButton) {
		this.actionButton = actionButton;
	}
	public String getImageClick() {
		return imageClick;
	}
	public void setImageClick(String imageClick) {
		this.imageClick = imageClick;
	}
	public String getRedirectToPage() {
		return redirectToPage;
	}
	public void setRedirectToPage(String redirectToPage) {
		this.redirectToPage = redirectToPage;
	}
}