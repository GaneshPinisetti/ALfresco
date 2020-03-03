/**
 * 
 */
package com.alfresco.verinon.utility.export.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfresco.verinon.utility.export.model.Node;
import com.alfresco.verinon.utility.export.service.SiteService;

/**
 * @author gtarafder
 *
 */

@RestController
public class NodesAPIController {
	
	@Autowired
	private SiteService siteService; 
	
	@GetMapping(value = {"/api/node/children"}, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Node> request(@RequestParam("nodeId") String nodeId) {
		List<Node> result = siteService.getChildrenBySiteId(nodeId);
        return result;
    }

}
