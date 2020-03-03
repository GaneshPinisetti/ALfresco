/**
 * 
 */
package com.alfresco.verinon.utility.export.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alfresco.verinon.utility.export.model.GeneralPaging;
import com.alfresco.verinon.utility.export.model.Node;
import com.alfresco.verinon.utility.export.model.SiteContentExport;
import com.alfresco.verinon.utility.export.model.SiteEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author gtarafder
 *
 */
@Service
public class SiteService {
	private static final String MAX_ITEMS = "3";

	@Value("${server.context.path}")
	private String serverContextPath;

	@Autowired
	AuthenticationService authenticationService;

	public List<SiteEntry> getSiteList() {
		String base64Creds = authenticationService.getBase64Credentials();
		List<SiteEntry> sites = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		String resourceURL = serverContextPath + "/alfresco/api/-default-/public/alfresco/versions/1/sites";

		ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
				GeneralPaging.class);
		System.out.println(responEntity.getBody().toString());
		GeneralPaging sitePaging = responEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		if (sitePaging != null) {
			if (sitePaging.getList() != null && sitePaging.getList().getEntries() != null) {
				sites = mapper.convertValue(sitePaging.getList().getEntries(), new TypeReference<List<SiteEntry>>() {
				});
			}
		}
		return sites;

	}

	public List<Node> getChildrenBySiteId(String nodeId) {
		String base64Creds = authenticationService.getBase64Credentials();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + base64Creds);

		// api endpoint
		HttpEntity<String> entity = new HttpEntity<>(headers);

		String resourceURL = serverContextPath + "/alfresco/api/-default-/public/alfresco/versions/1/nodes/" + nodeId
				+ "/children";
		System.out.println("resourceURL" + resourceURL);
		// trigger api request
		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
					new ParameterizedTypeReference<Object>() {
					});
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_IMPLEMENTED);
		} catch (RestClientException e) {
			throw e;
		}

		List<Node> children = new ArrayList<Node>();
		String result = new Gson().toJson(responseEntity.getBody());
		JsonObject obj = new Gson().fromJson(result, JsonObject.class);
		JsonArray entries = obj.get("list").getAsJsonObject().get("entries").getAsJsonArray();
		for (int i = 0; i < entries.size(); i++) {
			Node child = new Node();
			child.setName(entries.get(i).getAsJsonObject().get("entry").getAsJsonObject().get("name").getAsString());
			child.setId(entries.get(i).getAsJsonObject().get("entry").getAsJsonObject().get("id").getAsString());
			child.setNodeType(
					entries.get(i).getAsJsonObject().get("entry").getAsJsonObject().get("nodeType").getAsString());
			child.setIsFolder(
					entries.get(i).getAsJsonObject().get("entry").getAsJsonObject().get("isFolder").getAsBoolean());
			children.add(child);
		}
		System.out.println(children.size());
		return children;
	}

	public int executeExport(SiteContentExport siteContentExport) {
		String base64Creds = authenticationService.getBase64Credentials();
		List<SiteEntry> sites = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		String resourceURL = serverContextPath + "/alfresco/s/utility/export/sitecontent?siteNodeId="
				+ siteContentExport.getSiteId() + "&nodeRef=" + siteContentExport.getNodeRef() + "&outDir="
				+ siteContentExport.getOutDir();

		if (siteContentExport.getIgnoreVersion() != null) {
			resourceURL = resourceURL + "&ignoreVersion=" + siteContentExport.getIgnoreVersion();
		}

		if (siteContentExport.getFromDate() != null) {
			resourceURL = resourceURL + "&fromDate=" + siteContentExport.getFromDate().getTime();
		}

		if (siteContentExport.getToDate() != null) {
			resourceURL = resourceURL + "&toDate=" + siteContentExport.getToDate().getTime();
		}

		if (siteContentExport.getVlevel() != null) {
			resourceURL = resourceURL + "&vlevel=" + siteContentExport.getVlevel();
		}

		System.out.println(resourceURL);

		try {
			ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
					GeneralPaging.class);

			GeneralPaging sitePaging = responEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			if (sitePaging != null) {
				if (sitePaging.getList() != null && sitePaging.getList().getEntries() != null) {
					sites = mapper.convertValue(sitePaging.getList().getEntries(),
							new TypeReference<List<SiteEntry>>() {
							});
				}
			}
		} catch (RestClientException e) {

		} catch (IllegalArgumentException e) {

		}

		return 0;
	}

	/**
	 * 
	 * @param container
	 * @param siteContentExport
	 * @param base64Creds
	 * @return
	 */
	public int executeExport(String container, SiteContentExport siteContentExport) {
		String base64Creds = authenticationService.getBase64Credentials();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		String resourceURL = serverContextPath + "/alfresco/s/utility/export/" + container + "?site="
				+ siteContentExport.getSiteId() + "&outDir=" + siteContentExport.getOutDir();

		/*
		 * if (siteContentExport.getIgnoreVersion() != null) { resourceURL = resourceURL
		 * + "&ignoreVersion=" + siteContentExport.getIgnoreVersion(); }
		 */

		if (siteContentExport.getFromDate() != null) {
			resourceURL = resourceURL + "&fromDate=" + siteContentExport.getFromDate().getTime();
		}

		if (siteContentExport.getToDate() != null) {
			resourceURL = resourceURL + "&toDate=" + siteContentExport.getToDate().getTime();
		}

		System.out.println(resourceURL);

		try {
			ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
					GeneralPaging.class);

			GeneralPaging sitePaging = responEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			if (sitePaging != null) {
				if (sitePaging.getList() != null && sitePaging.getList().getEntries() != null) {
					mapper.convertValue(sitePaging.getList().getEntries(), new TypeReference<List<SiteEntry>>() {
					});
				}
			}
		} catch (RestClientException e) {

		} catch (IllegalArgumentException e) {

		}

		return 0;
	}

	/**
	 * 
	 * @param container
	 * @param siteContentExport
	 * @param base64Creds
	 * @return
	 */
	public int executeExportCategories(SiteContentExport siteContentExport) {
		String base64Creds = authenticationService.getBase64Credentials();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		String resourceURL = serverContextPath + "/alfresco/s/utility/export/categories?outDir="
				+ siteContentExport.getOutDir();

		if (siteContentExport.getFromDate() != null) {
			resourceURL = resourceURL + "&fromDate=" + siteContentExport.getFromDate().getTime();
		}

		if (siteContentExport.getToDate() != null) {
			resourceURL = resourceURL + "&toDate=" + siteContentExport.getToDate().getTime();
		}

		System.out.println(resourceURL);

		try {
			ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
					GeneralPaging.class);

			GeneralPaging sitePaging = responEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			if (sitePaging != null) {
				if (sitePaging.getList() != null && sitePaging.getList().getEntries() != null) {
					mapper.convertValue(sitePaging.getList().getEntries(), new TypeReference<List<SiteEntry>>() {
					});
				}
			}
		} catch (RestClientException e) {

		} catch (IllegalArgumentException e) {

		}

		return 0;
	}

	public Object relayGet(String url) {
		String base64Creds = authenticationService.getBase64Credentials();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + base64Creds);

		// api endpoint
		HttpEntity<String> entity = new HttpEntity<>(headers);

		String resourceURL = serverContextPath + url;
		System.out.println("resourceURL" + resourceURL);
		// trigger api request
		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
					new ParameterizedTypeReference<Object>() {
					});
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_IMPLEMENTED);
		} catch (RestClientException e) {
			throw e;
		}

		return responseEntity.getBody();
	}

	public Object getFolderListForSite(String siteId, String skipCount) {
		String query = "{\r\n" + "      \"query\": {\r\n"
				+ "        \"query\": \"PATH:\\\"/app:company_home/st:sites/cm:" + siteId
				+ "/cm:documentLibrary//*\\\" AND TYPE:\\\"cm:folder\\\"\",\r\n"
				+ "        \"language\": \"lucene\"\r\n" + "      },\r\n"
				+ "      \"include\": [\"properties\", \"path\", \"aspectNames\", \"isLink\", \"allowableOperations\", \"association\"],\r\n"
				+ "      \"paging\": {\r\n" + "		\"maxItems\": \"" + SiteService.MAX_ITEMS + "\",\r\n"
				+ "		\"skipCount\": \"" + skipCount + "\"\r\n" + "	  }\r\n" + "}";
		String base64Creds = authenticationService.getBase64Credentials();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity entity = new HttpEntity(query, headers);
		String resourceURL = serverContextPath + "alfresco/api/-default-/public/search/versions/1/search";
		ResponseEntity<GeneralPaging> responEntity = restTemplate.exchange(resourceURL, HttpMethod.POST, entity,
				GeneralPaging.class);
		GeneralPaging resultPaging = responEntity.getBody();
		return resultPaging;
	}

	public Object searchFolder(String siteId) {
		boolean hasMoreItems = true;
		int maxItem = 3;
		int skipCount = 0;
		while (hasMoreItems) {
			Object result = this.getFolderListForSite(siteId, Integer.toString(skipCount));
			String jsonString = new Gson().toJson(result);
			System.out.println(jsonString);
			JsonObject obj = new Gson().fromJson(jsonString, JsonObject.class);
			hasMoreItems = obj.get("list").getAsJsonObject().get("pagination").getAsJsonObject().get("hasMoreItems")
					.getAsBoolean();
			if(hasMoreItems) {
				skipCount = skipCount + maxItem;
			}
		}
		return null;
	}

}
