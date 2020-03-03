/**
 * 
 */
package com.alfresco.verinon.utility.export.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author goura
 *
 */
@Service
public class AuthenticationService {
	
	@Value( "${admin.user.username}" )
	private String userName;
	
	@Value( "${admin.user.password}" )
	private String password;

	public String getBase64Credentials() {
		return Base64.getEncoder().encodeToString((userName +":" + password).getBytes());
	}
}
