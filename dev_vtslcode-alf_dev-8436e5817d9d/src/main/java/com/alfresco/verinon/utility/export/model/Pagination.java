/**
 * 
 */
package com.alfresco.verinon.utility.export.model;

/**
 * @author gtarafder
 *
 */
public class Pagination {
	private Integer count;
	private boolean hasMoreItems;
	private Integer totalItems;
	private Integer skipCount;
	private Integer maxItems;
	@Override
	public String toString() {
		return "Pagination [count=" + count + ", hasMoreItems=" + hasMoreItems + ", totalItems=" + totalItems
				+ ", skipCount=" + skipCount + ", maxItems=" + maxItems + ", getCount()=" + getCount()
				+ ", isHasMoreItems()=" + isHasMoreItems() + ", getTotalItems()=" + getTotalItems()
				+ ", getSkipCount()=" + getSkipCount() + ", getMaxItems()=" + getMaxItems() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public boolean isHasMoreItems() {
		return hasMoreItems;
	}
	public void setHasMoreItems(boolean hasMoreItems) {
		this.hasMoreItems = hasMoreItems;
	}
	public Integer getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
	public Integer getSkipCount() {
		return skipCount;
	}
	public void setSkipCount(Integer skipCount) {
		this.skipCount = skipCount;
	}
	public Integer getMaxItems() {
		return maxItems;
	}
	public void setMaxItems(Integer maxItems) {
		this.maxItems = maxItems;
	}

}
