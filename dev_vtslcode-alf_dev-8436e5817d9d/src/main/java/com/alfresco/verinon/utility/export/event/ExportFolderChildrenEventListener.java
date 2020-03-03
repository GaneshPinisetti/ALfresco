/**
 * 
 */
package com.alfresco.verinon.utility.export.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alfresco.verinon.utility.export.dto.ExportTask;
import com.alfresco.verinon.utility.export.dto.ExportTaskRepository;
import com.alfresco.verinon.utility.export.dto.FileNode;
import com.alfresco.verinon.utility.export.dto.FileNodeRepository;
import com.alfresco.verinon.utility.export.dto.Folder;
import com.alfresco.verinon.utility.export.dto.FolderRepository;
import com.alfresco.verinon.utility.export.model.GeneralPaging;
import com.alfresco.verinon.utility.export.model.SiteContentExport;
import com.alfresco.verinon.utility.export.service.AuthenticationService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author goura
 *
 */
@Component
public class ExportFolderChildrenEventListener implements ApplicationListener<ExportFolderChildrenEvent> {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private FolderRepository folderRepository;

	@Autowired
	private FileNodeRepository fileNodeRepository;

	@Autowired
	private ExportTaskRepository exportTaskRepository;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Value("${server.context.path}")
	private String serverContextPath;

	private String MAX_ITEMS = "100";
	private int maxItem = 100;
	private String base64Creds;
	private List<String> pages = new ArrayList<String>();
	private List<String> nodes = new ArrayList<String>();
	private List<FileNode> files = new ArrayList<FileNode>();

	@Override
	public void onApplicationEvent(ExportFolderChildrenEvent event) {

		ExportTask et = new ExportTask();
		et.setJobRef(event.getJobRef());
		et.setStartTime(new Date());
		et.setTaskType("T2");
		et.setStatus("STARTED");

		et = this.exportTaskRepository.save(et);

		this.base64Creds = authenticationService.getBase64Credentials();

		List<Folder> folders = new ArrayList<Folder>();
		folders = this.folderRepository.findByJobRefAndNodeType(event.getJobRef(), "cm:folder");
		if (folders != null && !folders.isEmpty()) {
			for (Folder f : folders) {
				export(f.getNodeId());
			}
		}
		for (String str : nodes) {
			Gson gson = new Gson();
			JsonObject obj = gson.fromJson(str, JsonObject.class);
			FileNode fileNode = new FileNode();
			fileNode.setName(obj.get("name").getAsString());
			fileNode.setNodeId(obj.get("id").getAsString());
			fileNode.setNodeType(obj.get("nodeType").getAsString());
			fileNode.setParentId(obj.get("parentId").getAsString());
			fileNode.setFile(obj.get("isFolder").getAsBoolean());
			fileNode.setFolder(obj.get("isFile").getAsBoolean());
			fileNode.setNodeJson(obj.toString());
			fileNode.setJobRef(event.getJobRef());
			this.files.add(fileNode);
		}
		this.fileNodeRepository.saveAll(files);

		et.setEndTime(new Date());
		et.setStatus("COMPLETED");
		et = this.exportTaskRepository.save(et);
		DownloadContentEvent downloadContentEvent = new DownloadContentEvent(this, event.getJobRef(), event.getSiteContentExport());
		applicationEventPublisher.publishEvent(downloadContentEvent);
	}

	private void export(String folderId) {
		boolean hasMoreItems = true;
		int maxItem = 100;
		int skipCount = 0;
		while (hasMoreItems) {
			Object result = this.getChildrenForFolder(serverContextPath, folderId, Integer.toString(skipCount));
			String jsonString = new Gson().toJson(result);
			JsonObject obj = new Gson().fromJson(jsonString, JsonObject.class);
			hasMoreItems = obj.get("list").getAsJsonObject().get("pagination").getAsJsonObject().get("hasMoreItems")
					.getAsBoolean();
			if (hasMoreItems) {
				skipCount = skipCount + maxItem;
			}
			this.getNodeString(jsonString);
			pages.add(jsonString);
		}
	}

	public Object getChildrenForFolder(String serverContextPath, String folderId, String skipCount) {
		System.out.println("get children for folder " + folderId);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity entity = new HttpEntity(headers);
		String resourceURL = serverContextPath + "alfresco/api/-default-/public/alfresco/versions/1/nodes/" + folderId
				+ "/children?skipCount=" + skipCount + "&maxItems=" + this.MAX_ITEMS;

		System.out.println(resourceURL);
		ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
				GeneralPaging.class);
		GeneralPaging resultPaging = responEntity.getBody();
		return resultPaging;
	}

	public void getNodeString(String page) {
		JsonObject obj = new Gson().fromJson(page, JsonObject.class);
		JsonArray entries = obj.get("list").getAsJsonObject().get("entries").getAsJsonArray();
		entries.forEach(entry -> {
			String entryObj = new Gson().fromJson(entry, JsonObject.class).get("entry").toString();
			this.nodes.add(entryObj);
		});
	}

}
