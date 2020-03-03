package com.alfresco.verinon.utility.export.model;

public class Site {
	private String id;
	private String guid;
	private String title;
	private String desription;
	private String visibility;
	private String preset;
	private String role;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Site [id=" + id + ", guid=" + guid + ", title=" + title + ", desription=" + desription + ", visibility="
				+ visibility + ", preset=" + preset + ", role=" + role + ", getId()=" + getId() + ", getGuid()="
				+ getGuid() + ", getTitle()=" + getTitle() + ", getDesription()=" + getDesription()
				+ ", getVisibility()=" + getVisibility() + ", getPreset()=" + getPreset() + ", getRole()=" + getRole()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getPreset() {
		return preset;
	}
	public void setPreset(String preset) {
		this.preset = preset;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
