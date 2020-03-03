/**
 * 
 */
package com.alfresco.verinon.utility.export.event;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.alfresco.verinon.utility.export.dto.ExportTask;
import com.alfresco.verinon.utility.export.dto.ExportTaskRepository;
import com.alfresco.verinon.utility.export.dto.FileNode;
import com.alfresco.verinon.utility.export.dto.FileNodeRepository;
import com.alfresco.verinon.utility.export.model.SiteContentExport;
import com.alfresco.verinon.utility.export.service.AuthenticationService;

import reactor.core.publisher.Flux;

/**
 * @author goura
 *
 */
@Component
public class DownloadContentEventListener implements ApplicationListener<DownloadContentEvent> {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private FileNodeRepository fileNodeRepository;

	@Autowired
	private ExportTaskRepository exportTaskRepository;

	@Value("${server.context.path}")
	private String serverContextPath;

	@Value("${out.dir.default}")
	private String defaultOutDir;

	private SiteContentExport siteContentExport;

	@Override
	public void onApplicationEvent(DownloadContentEvent event) {
		this.siteContentExport = event.getSiteContentExport();
		System.out.println("File download - Started");
		ExportTask et = new ExportTask();
		et.setJobRef(event.getJobRef());
		et.setStartTime(new Date());
		et.setTaskType("T4");
		et.setStatus("STARTED");

		et = this.exportTaskRepository.save(et);

		String outDir = this.siteContentExport.getOutDir();
		if (outDir == null)
			outDir = this.defaultOutDir;
		outDir = outDir + File.separator + event.getJobRef();

		try {
			File dir = new File(outDir);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					System.out.println("createFolder failed to create path : " + outDir);
				} else {
					System.out.println("createFolder path : " + outDir);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		outDir = outDir + File.separator;

		List<FileNode> files = this.fileNodeRepository.findByJobRefAndNodeTypeNot(event.getJobRef(), "cm:folder");

		for (FileNode file : files) {
			try {
				downloadNoadContent(file, outDir);
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
		}

		et.setEndTime(new Date());
		et.setStatus("COMPLETED");
	}

	public void downloadNoadContent(FileNode file, String outDir) throws URISyntaxException, IOException {
		WebClient webClient = WebClient.create();
		OutputStream outputStream = new FileOutputStream(outDir + file.getNodeId() + "_" + file.getName());
		URI fileUrl = new URI(serverContextPath + "alfresco/api/-default-/public/alfresco/versions/1/nodes/"
				+ file.getNodeId() + "/content?attachment=true");
		// Request service to get file data
		Flux<DataBuffer> fileDataStream = webClient.get().uri(fileUrl)
				.header("Authorization", "Basic " + this.authenticationService.getBase64Credentials())
				.accept(MediaType.APPLICATION_OCTET_STREAM).retrieve().bodyToFlux(DataBuffer.class);

		// Streams the stream from response instead of loading it all in memory
		DataBufferUtils.write(fileDataStream, outputStream).map(DataBufferUtils::release).doOnError(throwable -> {
			System.out.println("Error - mark as failed");
		}).then().doFinally(onFinally -> {
			System.out.println(onFinally.toString());
		}).doOnTerminate(() -> {
			System.out.println();
		}).doOnSuccess(aVoid -> {
			System.out.println("Completed - mark as downloaded");
		}).log().block();
	}
}
