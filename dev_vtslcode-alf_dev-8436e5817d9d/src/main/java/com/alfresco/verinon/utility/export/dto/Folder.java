/**
 * 
 */
package com.alfresco.verinon.utility.export.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author goura
 *
 */

@Entity
@Table(name = "FOLDER")
public class Folder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "NODE_ID")
	private String nodeId;
	@Column(name = "NODE_TYPE")
	private String nodeType;
	@Column(name = "PARENT_ID")
	private String parentId;
	@Column(name = "PATH")
	private String path;
	
	@Lob
	@Column(name = "NODE_JSON")
	private String nodeJson;
	
	@Column(name = "JOB_REF")
	private String jobRef;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNodeJson() {
		return nodeJson;
	}

	public void setNodeJson(String nodeJson) {
		this.nodeJson = nodeJson;
	}
	
	public String getJobRef() {
		return jobRef;
	}

	public void setJobRef(String jobRef) {
		this.jobRef = jobRef;
	}
}
