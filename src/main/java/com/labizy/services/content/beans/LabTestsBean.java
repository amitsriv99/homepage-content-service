package com.labizy.services.content.beans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabTestsBean {
	private String id;
	
	@JsonProperty("labIds")
	private ArrayList<String> labIds;
	
	private String type;
	private String name;
	private String freeText;
	private String tags;
	private String actionButton;
	private String redirectToPage;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<String> getLabIds() {
		return labIds;
	}
	public void setLabIds(ArrayList<String> labIds) {
		this.labIds = labIds;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getActionButton() {
		return actionButton;
	}
	public void setActionButton(String actionButton) {
		this.actionButton = actionButton;
	}
	public String getRedirectToPage() {
		return redirectToPage;
	}
	public void setRedirectToPage(String redirectToPage) {
		this.redirectToPage = redirectToPage;
	}
}
