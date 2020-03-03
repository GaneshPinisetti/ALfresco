/**
 * 
 */
package com.alfresco.verinon.utility.export.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author gtarafder
 *
 */
public class SiteContentExport {

	private String siteId;
	private String nodeRef;
	private String outDir;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date fromDate;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date toDate;
	
	private Integer vlevel;
	
	private Boolean ignoreVersion;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Boolean getIgnoreVersion() {
		return ignoreVersion;
	}

	public void setIgnoreVersion(Boolean ignoreVersion) {
		this.ignoreVersion = ignoreVersion;
	}

	public String getOutDir() {
		return outDir;
	}

	public void setOutDir(String outDir) {
		this.outDir = outDir;
	}

	public Integer getVlevel() {
		return vlevel;
	}

	public void setVlevel(Integer vlevel) {
		this.vlevel = vlevel;
	}


	@Override
	public String toString() {
		return "SiteContentExport [siteId=" + siteId + ", nodeRef=" + nodeRef + ", outDir=" + outDir + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", vlevel=" + vlevel + "]";
	}

}
