package com.codespace.googleapps.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

@Component
public class ClientAuthorization {
	
	private static final List<String> CALENDAR_SCOPE = Arrays.asList(
			CalendarScopes.CALENDAR_READONLY, CalendarScopes.CALENDAR);

	private static final String CLIENT_ID = "GoogleApp";

	private static final String APPLICATION_NAME = "Google App";

	@Autowired
	private HttpTransport httpTransport;

	@Autowired
	private JsonFactory jsonFactory;
	
	@Autowired
	private FileDataStoreFactory dataStoreFactory;

	public Calendar getService() throws GeneralSecurityException, IOException {
		Credential credential = getCredential();
		
		return new com.google.api.services.calendar.Calendar
				.Builder(httpTransport, jsonFactory, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	public Credential getCredential() throws GeneralSecurityException, IOException {
		InputStream in = ClientAuthorization.class
				.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				jsonFactory, new InputStreamReader(in));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
				.Builder(httpTransport, jsonFactory, clientSecrets, CALENDAR_SCOPE)
				.setDataStoreFactory(dataStoreFactory)
				.setClientId(CLIENT_ID)
				.build();
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		return credential;
	}
	
}
