/**
 * 
 */
package com.alfresco.verinon.utility.export.event;

import org.springframework.context.ApplicationEvent;

import com.alfresco.verinon.utility.export.model.SiteContentExport;

/**
 * @author goura
 *
 */
public class DownloadContentEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8238805043948123430L;

	private String jobRef;

	private SiteContentExport siteContentExport;
	
	public DownloadContentEvent(Object source, String jobRef, SiteContentExport siteContentExport) {
		super(source);
		this.setJobRef(jobRef);
		this.siteContentExport = siteContentExport;
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
