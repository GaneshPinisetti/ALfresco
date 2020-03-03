/**
 * 
 */
package com.alfresco.verinon.utility.export.service.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.alfresco.verinon.utility.export.dto.ExportJob;
import com.alfresco.verinon.utility.export.dto.ExportJobRepository;
import com.alfresco.verinon.utility.export.event.ExportFolderEvent;
import com.alfresco.verinon.utility.export.model.SiteContentExport;
import com.alfresco.verinon.utility.export.service.AuthenticationService;

import reactor.core.publisher.Flux;

/**
 * @author goura
 *
 */

@Service
public class FolderService {
	@Value("${server.context.path}")
	private String serverContextPath;
	
	@Autowired
	private ExportJobRepository exportJobRepository;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	private AuthenticationService authenticationService;
	
//	Long jobId = null;
	public String searchFolder(SiteContentExport siteContentExport) {
		String jobTracker = UUID.randomUUID().toString();
		ExportJob job = new ExportJob();
		job.setJobRef(jobTracker);
		job.setJobType("J1");
		job.setStartTime(new Date());
		job.setStatus("STARTED");
		this.exportJobRepository.save(job);
		ExportFolderEvent exportFolderEvent = new ExportFolderEvent(this, jobTracker, siteContentExport, null, null);
        applicationEventPublisher.publishEvent(exportFolderEvent);
		
		return jobTracker;
	}
	
	
	

//		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
//		jobParametersBuilder.addLong("time", System.currentTimeMillis()).toJobParameters();
//		jobParametersBuilder.addString("serverContextPath", serverContextPath);
//		jobParametersBuilder.addString("jobTracker", jobTracker);
//		jobParametersBuilder.addString("base64Creds", base64Creds);
//		jobParametersBuilder.addString("siteId", siteId);
//		
//		try {
//			 JobExecution jobExecution = jobLauncher.run(processJob, jobParametersBuilder.toJobParameters());
//			 jobId = jobExecution.getJobId();
//		} catch (JobExecutionAlreadyRunningException e) {
//			e.printStackTrace();
//		} catch (JobRestartException e) {
//			e.printStackTrace();
//		} catch (JobInstanceAlreadyCompleteException e) {
//			e.printStackTrace();
//		} catch (JobParametersInvalidException e) {
//			e.printStackTrace();
//		}
}
