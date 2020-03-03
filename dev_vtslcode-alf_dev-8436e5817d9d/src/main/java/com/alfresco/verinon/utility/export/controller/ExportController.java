/**
 * 
 */
package com.alfresco.verinon.utility.export.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alfresco.verinon.utility.export.model.SiteContentExport;
import com.alfresco.verinon.utility.export.service.SiteService;
import com.alfresco.verinon.utility.export.service.files.FolderService;

/**
 * @author Gouranga Tarafder
 *
 */
@Controller
public class ExportController {
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	FolderService folderService;
	


	@GetMapping(path = "/dashboard")
	public String loadIndexPage(@RequestParam(required = false) String status, Model model,
			HttpServletRequest request) {
		return "index";
	}

	@GetMapping(path = "/export/application/{component}")
	public String exportApplicationComponent(@PathVariable(required = true) String component, Model model,
			HttpServletRequest request) {
		System.out.println("component:" + component);
		String pageTitle = "Export - ";
		
		switch(component) {
		case "sites":
			pageTitle = pageTitle + "Sites";
			break;
		case "users":
			pageTitle = pageTitle + "Users";
			break;
		case "groups":
			pageTitle = pageTitle + "Groups";
			break;
		case "task":
			pageTitle = pageTitle + "Task";
			break;
		default:
			break;
		}
		
		model.addAttribute("pageTitle", pageTitle);
		
		return "application-component";
	}
	
	
	@GetMapping(path="/export/site/{component}")
	public String exportSiteComponent(@PathVariable(required = true) String component, Model model, 
			HttpServletRequest request) {
		String pageTitle = "Export Site - ";
		
		switch(component) {
		case "membersAndGroups":
			pageTitle = pageTitle + "Members & Groups";
			break;
		case "content":
			pageTitle = pageTitle + "Content";
			break;
		case "discussions":
			pageTitle = pageTitle + "Discussions";
			break;
		case "calendar-or-events":
			pageTitle = pageTitle + "Calendar/Events";
			break;
		case "dataList":
			pageTitle = pageTitle + "Data List";
			break;
		case "links":
			pageTitle = pageTitle + "Links";
			break;
		case "blog-posts":
			pageTitle = pageTitle + "Blog Posts";
			break;
		case "wiki":
			pageTitle = pageTitle + "Wiki";
			break;
		case "categories":
			pageTitle = pageTitle + "Categories";
			break;
		default:
			break;
		}
		
		model.addAttribute("title", "Export Utility - "+pageTitle);
		model.addAttribute("siteList", this.siteService.getSiteList());
		model.addAttribute("siteContentExport", new SiteContentExport());
		model.addAttribute("status", "");
		model.addAttribute("pageTitle", pageTitle);
		return "site-component";
	}
	
	@PostMapping(path="/export/site/{page}")
	public String executeExport(@PathVariable(required = true) String page, @ModelAttribute SiteContentExport siteContentExport , HttpServletRequest request) {
		System.out.println(siteContentExport);
		this.folderService.searchFolder(siteContentExport);
//		try {
//			this.folderService.downloadNoadContent();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return "redirect:/export/site/"+page;
	}
	
	@GetMapping("/reports/application/{systemLevelReports}")
	public String systemLevelReports(@PathVariable(required = true) String systemLevelReports,
			Model model, HttpServletRequest request) {
		String pageTitle = "Reports - ";
		
		switch (systemLevelReports) {
		case "workflow-in-progress":
			pageTitle = pageTitle + "Workflow in Progress";
			break;
		case "workflow-summary":
			pageTitle = pageTitle + "Workflow Summary";
			break;
		default:
			break;
		}
		model.addAttribute("pageTitle", pageTitle);
		return "system-level-reports";
	}
	
	
	
	
	
	@GetMapping("/reports/site/{siteLevelReports}")
	public String siteLevelReports(@PathVariable(required = true) String siteLevelReports,
			Model model, HttpServletRequest request) {
		String pageTitle = "Reports - ";
		
		switch (siteLevelReports) {
		case "scoring-marix-report":
			pageTitle = pageTitle + "Scoring matrix report";
			break;
		case "object-detailed-report":
			pageTitle = pageTitle + "Object detailed report";
			break;
		case "user-detailed-report":
			pageTitle = pageTitle + "User detailed report";
			break;
		case "roles-detailed-report":
			pageTitle = pageTitle + "Roles detailed report";
			break;
		case "groups-detailed-report":
			pageTitle = pageTitle + "Groups detailed report";
			break;
		case "file-size-report":
			pageTitle = pageTitle + "File size report";
			break;
		case "special-chracter-report":
			pageTitle = pageTitle + "Special character report";
			break;
		case "url-issue-report":
			pageTitle = pageTitle + "URL issue report";
			break;
		case "version-mismatch":
			pageTitle = pageTitle + "Version Mismatch";
			break;
		default:
			break;
		}
		
		return "site-level-reports";
	}

}
