/**
 * 
 */
package com.alfresco.verinon.utility.export.model;

import java.util.List;

/**
 * @author gtarafder
 *
 */
public class InlineModel {
	private Pagination pagination;
	private List<Object> entries;
	
	
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public List<Object> getEntries() {
		return entries;
	}
	public void setEntries(List<Object> entries) {
		this.entries = entries;
	}
}
