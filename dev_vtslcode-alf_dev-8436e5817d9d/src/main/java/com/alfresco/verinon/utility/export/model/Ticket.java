package com.alfresco.verinon.utility.export.model;

public class Ticket {
	private String id;
	private String userId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", userId=" + userId + "]";
	}
}
