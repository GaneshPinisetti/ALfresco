/**
 * 
 */
package com.alfresco.verinon.utility.export.dto;

import java.util.Date;

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
@Table(name = "EXPORT_TASK")
public class ExportTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "TASK_REF")
	private String taskRef;

	@Column(name = "JOB_REF")
	private String jobRef;

	@Column(name = "TASK_TYPE")
	private String taskType;

	@Column(name = "START_TIME")
	private Date startTime;

	@Column(name = "END_TIME")
	private Date endTime;

	@Column(name = "STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskRef() {
		return taskRef;
	}

	public void setTaskRef(String taskRef) {
		this.taskRef = taskRef;
	}

	public String getJobRef() {
		return jobRef;
	}

	public void setJobRef(String jobRef) {
		this.jobRef = jobRef;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
