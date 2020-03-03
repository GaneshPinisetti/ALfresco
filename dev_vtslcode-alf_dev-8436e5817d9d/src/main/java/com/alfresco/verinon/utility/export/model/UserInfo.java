package com.alfresco.verinon.utility.export.model;

public class UserInfo {
	private String displayName;
	private String id;
	@Override
	public String toString() {
		return "UserInfo [displayName=" + displayName + ", id=" + id + ", getDisplayName()=" + getDisplayName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
