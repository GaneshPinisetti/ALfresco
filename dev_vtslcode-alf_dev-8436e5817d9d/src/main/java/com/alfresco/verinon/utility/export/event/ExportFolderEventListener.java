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
import com.alfresco.verinon.utility.export.dto.Folder;
import com.alfresco.verinon.utility.export.dto.FolderRepository;
import com.alfresco.verinon.utility.export.model.GeneralPaging;
import com.alfresco.verinon.utility.export.model.SiteContentExport;
import com.alfresco.verinon.utility.export.service.AuthenticationService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author goura
 *
 */
@Component
public class ExportFolderEventListener implements ApplicationListener<ExportFolderEvent> {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private FolderRepository folderRepository;

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
	private List<String> folderStrs = new ArrayList<String>();

	@Override
	public void onApplicationEvent(ExportFolderEvent event) {

		ExportTask et = new ExportTask();
		et.setJobRef(event.getJobRef());
		et.setStartTime(new Date());
		et.setTaskType("T1");
		et.setStatus("STARTED");

		et = this.exportTaskRepository.save(et);

		this.base64Creds = authenticationService.getBase64Credentials();
		System.out.println(event.toString());
		export(event.getSiteContentExport().getSiteId());

		List<Folder> folders = new ArrayList<Folder>();

		for (String str : folderStrs) {
			Gson gson = new Gson();
			JsonObject obj = gson.fromJson(str, JsonObject.class);
			Folder folder = new Folder();
			folder.setName(obj.get("name").getAsString());
			folder.setNodeId(obj.get("id").getAsString());
			folder.setNodeType(obj.get("nodeType").getAsString());
			folder.setParentId(obj.get("parentId").getAsString());
			folder.setPath(obj.get("path").getAsJsonObject().get("name").getAsString());
			folder.setNodeJson(obj.toString());
			folder.setJobRef(event.getJobRef());
			folders.add(folder);
		}
		this.folderRepository.saveAll(folders);

		et.setEndTime(new Date());
		et.setStatus("COMPLETED");
		ExportFolderChildrenEvent exportFolderChildrenEvent = new ExportFolderChildrenEvent(this, event.getJobRef(), event.getSiteContentExport());
        applicationEventPublisher.publishEvent(exportFolderChildrenEvent);
	}

	private void export(String siteId) {
		boolean hasMoreItems = true;
		int skipCount = 0;
		while (hasMoreItems) {
			Object result = this.getFolderListForSite(serverContextPath, siteId, Integer.toString(skipCount));
			String jsonString = new Gson().toJson(result);
			JsonObject obj = new Gson().fromJson(jsonString, JsonObject.class);
			hasMoreItems = obj.get("list").getAsJsonObject().get("pagination").getAsJsonObject().get("hasMoreItems")
					.getAsBoolean();
			if (hasMoreItems) {
				skipCount = skipCount + maxItem;
			}
			this.getFolderString(jsonString);
			pages.add(jsonString);
		}
	}

	public Object getFolderListForSite(String serverContextPath, String siteId, String skipCount) {
		String query = "{\r\n" + "      \"query\": {\r\n"
				+ "        \"query\": \"PATH:\\\"/app:company_home/st:sites/cm:" + siteId
				+ "/cm:documentLibrary//*\\\" AND TYPE:\\\"cm:folder\\\"\",\r\n"
				+ "        \"language\": \"lucene\"\r\n" + "      },\r\n"
				+ "      \"include\": [\"properties\", \"path\", \"aspectNames\", \"isLink\", \"allowableOperations\", \"association\"],\r\n"
				+ "      \"paging\": {\r\n" + "		\"maxItems\": \"" + this.MAX_ITEMS + "\",\r\n"
				+ "		\"skipCount\": \"" + skipCount + "\"\r\n" + "	  }\r\n" + "}";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		System.out.println(base64Creds);
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity entity = new HttpEntity(query, headers);
		String resourceURL = serverContextPath + "alfresco/api/-default-/public/search/versions/1/search";
		System.out.println(siteId);
		System.out.println(resourceURL);
		ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.POST, entity,
				GeneralPaging.class);
		GeneralPaging resultPaging = responEntity.getBody();
		return resultPaging;
	}

	public void getFolderString(String page) {
		JsonObject obj = new Gson().fromJson(page, JsonObject.class);
		JsonArray entries = obj.get("list").getAsJsonObject().get("entries").getAsJsonArray();
		System.out.println(entries.size());
		for (JsonElement entry : entries) {
			String entryObj = new Gson().fromJson(entry, JsonObject.class).get("entry").toString();
			this.folderStrs.add(entryObj);
		}
	}

}
