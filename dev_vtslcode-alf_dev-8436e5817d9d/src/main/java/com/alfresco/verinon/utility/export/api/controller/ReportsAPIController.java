/**
 * 
 */
package com.alfresco.verinon.utility.export.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfresco.verinon.utility.export.service.SiteService;

/**
 * @author gtarafder
 *
 */

@RestController
public class ReportsAPIController {
	
	@Autowired
	private SiteService siteService; 
	
//	@Autowired
//	private ExportTasks exportTasks;
	
	@GetMapping(value = {"/api/reports/scoring-matrix-report"}, produces = APPLICATION_JSON_VALUE)
    public Object scoringMatrixReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/scoringmatrix?sites="+sites;
		return siteService.relayGet(url);
    }
	
	@GetMapping(value = {"/api/reports/file-size-report"}, produces = APPLICATION_JSON_VALUE)
    public Object request(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/filesizereport?sites="+sites;
		return siteService.relayGet(url);
    }
	
	@GetMapping(value = {"/api/reports/special-character-report"}, produces = APPLICATION_JSON_VALUE)
    public Object specialCharacterReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/specialcharacterreport?sites="+sites;
		return siteService.relayGet(url);
    }

	@GetMapping(value = {"/api/reports/url-issue-report"}, produces = APPLICATION_JSON_VALUE)
    public Object specialUrlIssueReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/urlissuereport?sites="+sites;
		return siteService.relayGet(url);
    }
	
	@GetMapping(value = {"/api/reports/version-mismatch-report"}, produces = APPLICATION_JSON_VALUE)
    public Object versionMismatchReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String url = "/alfresco/s/utility/report/versionmismatchreport?sites="+sites;
		return siteService.relayGet(url);
    }
	
	@GetMapping(value = {"/api/reports/user-detailed-report"}, produces = APPLICATION_JSON_VALUE)
    public Object userDetailedReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/userdetailedreport?sites="+sites;
		return siteService.relayGet(url);
    }
	
	@GetMapping(value = {"/api/reports/groups-detailed-report"}, produces = APPLICATION_JSON_VALUE)
    public Object groupsDetailedReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/groupsdetailedreport?sites="+sites;
		return siteService.relayGet(url);
    }
	
	@GetMapping(value = {"/api/reports/roles-detailed-report"}, produces = APPLICATION_JSON_VALUE)
    public Object rolesDetailedReport(@RequestParam("sites") String sites, HttpServletRequest request) {
		String url = "/alfresco/s/utility/report/rolesdetailedreport?sites="+sites;
		return siteService.relayGet(url);
    }
	
//	@GetMapping(value = {"/api/reports/workflow-summary-report"}, produces = APPLICATION_JSON_VALUE)
//    public Object workflowSummaryReport(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		String base64Creds = (session.getAttribute("base64Creds")!=null)? session.getAttribute("base64Creds").toString():null;
//		List<WorkflowSummary> workflowSummaries = new ArrayList<WorkflowSummary>();
//		List<Process> processess = exportTasks.getProcessess(base64Creds);
//		if (processess != null) {
//			for (Process p : processess) {
//				if (p.process != null) {
//					WorkflowSummary wSummary = new WorkflowSummary();
//					try {
//						wSummary.setCompleted(p.process.get("completed").getAsString());
//					} catch (Exception e) {
//					}
//					try {
//						wSummary.setProcessDefinitionId(p.process.get("processDefinitionId").getAsString());
//					} catch (Exception e) {
//					}
//					try {
//						wSummary.setProcessDefinitionKey(p.process.get("processDefinitionKey").getAsString());
//					} catch (Exception e) {
//					}
//					try {
//						wSummary.setStartedAt(p.process.get("startedAt").getAsString());
//					} catch (Exception e) {
//					}
//					try {
//						wSummary.setWorkflowCreatedBy(p.process.get("startUserId").getAsString());
//					} catch (Exception e) {
//					}
//					try {
//						wSummary.setWorkFlowID(p.process.get("id").getAsString());
//					} catch (Exception e) {
//					}
//					workflowSummaries.add(wSummary);
//				}
//			}
//		}
//		
//		ReportData rd = new ReportData();
//		rd.data = workflowSummaries;
//		return rd;
//    }
	
//	@GetMapping(value = {"/api/reports/workflow-detailed-report"}, produces = APPLICATION_JSON_VALUE)
//    public Object workflowDetailedReport(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		String base64Creds = (session.getAttribute("base64Creds")!=null)? session.getAttribute("base64Creds").toString():null;
//		List<WorkflowDetails> workflowDetails = new ArrayList<WorkflowDetails>();
//		List<Process> processess = exportTasks.getProcessess(base64Creds);
//		if (processess != null) {
//			for (Process p : processess) {
//				if (p.process != null && p.tasksObj!=null) {
//					for(Task t:p.tasksObj) {
//						WorkflowDetails wd= new WorkflowDetails();
//						try {
//							if(p.process.get("completed").getAsBoolean())
//								wd.setWorkflowStatus("Completed");
//							else
//								wd.setWorkflowStatus("In Progress");
//						} catch (Exception e) {
//						}
//						try {
//							wd.setWorkFlowID(p.process.get("id").getAsString());
//						} catch (Exception e) {
//						}
//						try {
//							wd.setWorkFlowName(p.process.get("processDefinitionKey").getAsString());
//						} catch (Exception e) {
//						}
//						try {
//							wd.setWorkflowCreateDate(p.process.get("startedAt").getAsString());
//						} catch (Exception e) {
//						}
//						try {
//							wd.setWorkflowCreatedBy(p.process.get("startUserId").getAsString());
//						} catch (Exception e) {
//						}
//
//						try {   wd.taskId 						= t.task.get("id").getAsString();                   } catch (Exception e) {}
//						try {   wd.taskName 					= t.task.get("name").getAsString();                 } catch (Exception e) {}
//						try {   wd.taskDescription 				= t.task.get("description").getAsString();          } catch (Exception e) {}
//						try {   wd.taskUrl 						= "/share/page/task-details?taskId=activiti$"+ wd.taskId ;    } catch (Exception e) {}
//						try {   wd.taskAssignee 				= t.task.get("assignee").getAsString();             } catch (Exception e) {}
//						try {   wd.taskState 					= t.task.get("state").getAsString();                } catch (Exception e) {}
//						try {   wd.taskPriority 				= t.task.get("priority").getAsString();             } catch (Exception e) {}
//						try {   wd.taskActivityDefinitionId 	= t.task.get("activityDefinitionId").getAsString(); } catch (Exception e) {}
//						try {   wd.taskdueAt 					= t.task.get("dueAt").getAsString();                } catch (Exception e) {}
//						try {   
//							if(t.items!=null) {
//								for(JsonObject item:t.items) {
//									try {
//										if(wd.wfTagetObjectID==null || wd.wfTagetObjectID.isEmpty()) {
//											wd.wfTagetObjectURL = "workspace://SpacesStore/" + item.get("id").getAsString();
//											wd.wfTagetObjectID = item.get("id").getAsString();
//										}
//										else {
//											wd.wfTagetObjectURL = wd.wfTagetObjectURL + ";" + "workspace://SpacesStore/" + item.get("id").getAsString();
//											wd.wfTagetObjectID = wd.wfTagetObjectID + ";" + item.get("id").getAsString();
//										}
//									} catch (Exception e) {	}
//									
//									try {
//										if(wd.wfTagetObjectName==null || wd.wfTagetObjectName.isEmpty())
//											wd.wfTagetObjectName = item.get("name").getAsString();
//										else
//											wd.wfTagetObjectName = wd.wfTagetObjectName +";" +item.get("name").getAsString();
//									} catch (Exception e) {	}									
//								}
//							} 
//						}catch (Exception e) {}
//						try {                        } catch (Exception e) {}
//				            
//						workflowDetails.add(wd);
//					}					
//				}
//			}
//		}
//		
//		ReportData rd = new ReportData();
//		rd.data = workflowDetails;
//		return rd;
//    }
}
