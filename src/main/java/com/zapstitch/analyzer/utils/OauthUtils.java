package com.zapstitch.analyzer.utils;

import java.io.IOException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.zapstitch.analyzer.base.Application;
import com.zapstitch.analyzer.base.Constants;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class OauthUtils {

	/**
	 * getCredential() method return credential using authcode
	 * @param authCode - authorization code
	 * @return user credential 
	 * @throws IOException
	 */
	public static Credential getCredential(final String authCode) throws IOException {

		GoogleAuthorizationCodeFlow flow = Application.getInstance().getAuthorizationCodeFlow();
		GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(Constants.CALLBACK_URI).execute();
		Credential credential = flow.createAndStoreCredential(response, null);
		return credential;

	}

	/**
	 * getLoginUrl() method return login url with login url
	 * @param - stateToken - token
	 * @return - login url
	 */
	public static String getLoginUrl(final String stateToken) {

		final GoogleAuthorizationCodeRequestUrl url = Application.getInstance().getAuthorizationCodeFlow()
				.newAuthorizationUrl();
		return url.setRedirectUri(Constants.CALLBACK_URI).setState(stateToken).build();
	}

	/**
	 * getUserInfoJson() method return user basic information in JSON format
	 * @param credential - credential to access google account
	 * @return return basic information in JSON format
	 * @throws IOException
	 */
	public static String getUserInfoJson(final Credential credential) throws IOException {

		HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		final GenericUrl url = new GenericUrl(Constants.USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType(Constants.JSON_CONTENT);
		final String jsonIdentity = request.execute().parseAsString();

		return jsonIdentity;
	}

	/**
	 * revokeAccess() method revoke the access
	 * @param credential - credential to access google account
	 * @return - return true if access revoke is successful otherwise false 
	 */
	public static boolean revokeAccess(final Credential credential) {

		boolean rovokeSuccessfully = true;
		try {
			HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
			GenericUrl url = new GenericUrl(Constants.REVOKE_URL + credential.getAccessToken());
			HttpRequest request;
			request = requestFactory.buildGetRequest(url);
			request.execute();
		} catch (IOException ioe) {
			rovokeSuccessfully = false;
		}

		return rovokeSuccessfully;
	}

}
