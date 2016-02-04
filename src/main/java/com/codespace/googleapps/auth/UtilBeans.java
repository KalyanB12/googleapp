package com.codespace.googleapps.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

@Configuration
public class UtilBeans {
	
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/googleapp");
	
	@Bean
	public HttpTransport httpTransport() throws IOException, GeneralSecurityException {
		return GoogleNetHttpTransport.newTrustedTransport();
	}
	
	@Bean
	public JsonFactory jsonFactory() {
		return JacksonFactory.getDefaultInstance();
	}
	
	@Bean
	public FileDataStoreFactory dataStoreFactory() throws IOException {
		return new FileDataStoreFactory(DATA_STORE_DIR);
	}
}
