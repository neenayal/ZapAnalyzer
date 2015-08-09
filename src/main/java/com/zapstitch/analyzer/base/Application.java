package com.zapstitch.analyzer.base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;

import org.json.simple.parser.JSONParser;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;


/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class Application {

	/** Application name. */
	private static final String APPLICATION_NAME = "zappyanalyzer";

	private static final String ACCESS_TYPE = "offline";
	
	private static final String CLIENT_ID = "XXX.apps.googleusercontent.com";

	private static final String CLIENT_SECRET = "XXX-XX";

	private static final Collection<String> SCOPE = Arrays.asList(
			"https://www.googleapis.com/auth/userinfo.profile",
			"https://www.googleapis.com/auth/userinfo.email", 
			"https://www.googleapis.com/auth/gmail.readonly");

	private static final int BATCH_SIZE = 10;

	private static final String DATE_FORMAT = "yyyy/MM/dd";

	private static Application instance = new Application();

	private HttpTransport  HTTP_TRANSPORT = new NetHttpTransport();

	private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	GoogleAuthorizationCodeFlow googleAuthFlow;

	/**
	 * Default Constructor
	 */
	private Application() {
		googleAuthFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID,
				CLIENT_SECRET, SCOPE).setAccessType(ACCESS_TYPE).build();
	}

	/**
	 * @param access
	 * @param refresh
	 * @return
	 */
	public Credential CreateCredential(final String access, final String refresh){
		 GoogleCredential credentials = new GoogleCredential.Builder().
				 setClientSecrets(CLIENT_ID, CLIENT_SECRET).
				 setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT).
				 build() .setRefreshToken(refresh).
				 setAccessToken(access);
		
		return credentials;
	}
	
	/**
	 * @return
	 */
	public static Application getInstance() {
		return instance;
	}

	/**
	 * @return
	 */
	public GoogleAuthorizationCodeFlow getAuthorizationCodeFlow() {
		return googleAuthFlow;
	}

	/**
	 * Build and return an authorized Gmail client service.
	 * 
	 * @return an authorized Gmail client service
	 * @throws IOException
	 */
	public Gmail getGmailService(Credential credential) throws IOException {
		return new Gmail.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPLICATION_NAME).build();
	}

	private static JSONParser parser = new JSONParser();

	private static SimpleDateFormat appDateFormatter = new SimpleDateFormat(DATE_FORMAT);

	/**
	 * 
	 */
	public void init() {
	}

	/**
	 * 
	 */
	public void destroy() {

	}

	/**
	 * @return
	 */
	public JSONParser getParser() {
		return parser;
	}

	/**
	 * @return
	 */
	public SimpleDateFormat getAppDateFormatter() {
		return appDateFormatter;
	}

	/**
	 * @return
	 */
	public int getBatchsize() {
		return BATCH_SIZE;
	}
	
}
