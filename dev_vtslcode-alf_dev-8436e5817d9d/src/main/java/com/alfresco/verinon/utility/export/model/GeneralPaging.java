package com.alfresco.verinon.utility.export.model;

public class GeneralPaging {
	private  InlineModel list;

	@Override
	public String toString() {
		return "SitePaging [list=" + list + ", getList()=" + getList() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public InlineModel getList() {
		return list;
	}

	public void setList(InlineModel list) {
		this.list = list;
	}
}
