package com.dell.enterprise.agenttool.model;

import java.io.Serializable;

public class SiteReferral implements Serializable {
	private String sourceID;
	private String sourceDescription;
	private String masterSource;
	
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public String getSourceDescription() {
		return sourceDescription;
	}
	public void setSourceDescription(String sourceDescription) {
		this.sourceDescription = sourceDescription;
	}
	public String getMasterSource() {
		return masterSource;
	}
	public void setMasterSource(String masterSource) {
		this.masterSource = masterSource;
	}
	
}