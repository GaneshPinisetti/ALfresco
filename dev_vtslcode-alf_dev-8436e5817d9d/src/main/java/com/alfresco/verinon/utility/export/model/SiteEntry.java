/**
 * 
 */
package com.alfresco.verinon.utility.export.model;

/**
 * @author gtarafder
 *
 */
public class SiteEntry {
	private Site entry;

	public Site getEntry() {
		return entry;
	}

	public void setEntry(Site entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return "SiteEntry [entry=" + entry + ", getEntry()=" + getEntry() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}