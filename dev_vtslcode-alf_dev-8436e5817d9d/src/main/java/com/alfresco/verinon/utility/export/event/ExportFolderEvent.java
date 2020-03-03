/**
 * 
 */
package com.alfresco.verinon.utility.export.event;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

import com.alfresco.verinon.utility.export.model.SiteContentExport;

/**
 * @author goura
 *
 */
public class ExportFolderEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8238805043948123430L;

	private String jobRef;
	private SiteContentExport siteContentExport;
	private Date startDate;
	private Date endDate;

	public ExportFolderEvent(Object source, String jobRef, SiteContentExport siteContentExport, Date startDate, Date endDate) {
		super(source);
		this.setJobRef(jobRef);
		this.setSiteContentExport(siteContentExport);
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getJobRef() {
		return jobRef;
	}

	public void setJobRef(String jobRef) {
		this.jobRef = jobRef;
	}

	public SiteContentExport getSiteContentExport() {
		return siteContentExport;
	}

	public void setSiteContentExport(SiteContentExport siteContentExport) {
		this.siteContentExport = siteContentExport;
	}

}
