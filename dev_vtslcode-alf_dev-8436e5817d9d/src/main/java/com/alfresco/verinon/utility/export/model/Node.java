/**
 * 
 */
package com.alfresco.verinon.utility.export.model;

/**
 * @author gtarafder
 *
 */
public class Node {
	
	private String id;
    private String name;
    private String nodeType;
    private Boolean isFolder; 
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public Boolean getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(Boolean isFolder) {
		this.isFolder = isFolder;
	}
	
	
    
	

}
