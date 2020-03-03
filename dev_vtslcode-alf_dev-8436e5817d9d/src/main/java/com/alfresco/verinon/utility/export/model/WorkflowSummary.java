/**
 * 
 */
package com.alfresco.verinon.utility.export.model;

/**
 * @author Gouranga
 *
 */
public class WorkflowSummary {

	private String workFlowID;
	private String processDefinitionId;
	private String processDefinitionKey;
	private String startedAt;
	private String workflowCreatedBy;
	private String completed;
	
	public String getWorkFlowID() {
		return workFlowID;
	}
	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
	
	public String getWorkflowCreatedBy() {
		return workflowCreatedBy;
	}
	public void setWorkflowCreatedBy(String workflowCreatedBy) {
		this.workflowCreatedBy = workflowCreatedBy;
	}
	public String getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
}
