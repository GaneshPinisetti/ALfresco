/**
 * 
 */
package com.alfresco.verinon.utility.export.common;

/**
 * @author Gouranga Tarafder
 *
 */
public class ProjectConstants {

	
	// versions : Retrieve and manage versionsShow / HideList OperationsExpand Operations
	public static final String GET_LIST_VERSION_HISTORY = "/nodes/{nodeId}/versions";
	public static final String DELETE_DELETE_A_VERSION =  "/nodes/{nodeId}/versions/{versionId}";
	//GET /nodes/{nodeId}/versions/{versionId}Get version information
	//GET /nodes/{nodeId}/versions/{versionId}/contentGet version content
	//POST /nodes/{nodeId}/versions/{versionId}/revertRevert a version
	
	
	//nodes : Retrieve and manage nodes
	public static final String GET_GET_A_NODE = "/nodes/{nodeId}";
	public static final String GET_LIST_NODE_CHILDREN = "/nodes/{nodeId}/children";
	public static final String GET_Get_NODE_CONTENT = "/nodes/{nodeId}/content";
	public static final String GET_LIST_PARENTS = "/nodes/{nodeId}/parents";
	public static final String GET_LIST_SECONDARY_CHILDREN = "/nodes/{nodeId}/secondary-children";
	public static final String GET_LIST_SOURCE_ASSOCIATIONS = "/nodes/{nodeId}/sources";
	public static final String GET_LIST_TARGET_ASSOCIATIONS = "/nodes/{nodeId}/targets";
	
	
	//groups : Retrieve and manage groups
	public static final String GET_LIST_GROUPS = "/groups";
	public static final String GET_GET_GROUP_DETAILS = "/groups/{groupId}";
	public static final String GET_LIST_MEMBERSHIPS_OF_A_GROUP = "/groups/{groupId}/members";
	public static final String GET_LIST_GROUP_MEMBERSHIPS = "/people/{personId}/groups";
	
	
	//people : Retrieve and manage people
	public static final String GET_LIST_PEOPLE = "/people";
	public static final String GET_GET_A_PERSON = "/people/{personId}";
	public static final String GET_GET_AVATAR_IMAGE = "/people/{personId}/avatar";
	
	
	//comments : Retrieve and manage comments
	public static final String GET_LIST_COMMENTS = "/nodes/{nodeId}/comments";
	
	
	//sites : Retrieve and manage sites
	public static final String GET_LIST_SITE_MEMBERSHIP_REQUESTS = "/people/{personId}/site-membership-requests";
	public static final String GET_GET_A_SITE_MEMBERSHIP_REQUEST = "/people/{personId}/site-membership-requests/{siteId}";
	//public static final String GET_LIST_SITE_MEMBERSHIPS = "/people/{personId}/sites";
	//public static final String GET_GET_A_SITE_MEMBERSHIP = "/people/{personId}/sites/{siteId}";
	public static final String GET_GET_SITE_MEMBERSHIP_REQUESTS = "/site-membership-requests";
	public static final String GET_LIST_SITES = "/sites";
	public static final String GET_GET_A_SITE = "/sites/{siteId}";
	public static final String GET_LIST_SITE_CONTAINERS = "/sites/{siteId}/containers";
	public static final String GET_GET_A_SITE_CONTAINER = "/sites/{siteId}/containers/{containerId}";
	public static final String GET_LIST_SITE_MEMBERSHIPS = "/sites/{siteId}/members";
	public static final String GET_GET_A_SITE_MEMBERSHIP = "/sites/{siteId}/members/{personId}";
	
	
}
