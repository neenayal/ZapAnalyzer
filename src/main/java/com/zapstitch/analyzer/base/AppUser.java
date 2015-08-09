package com.zapstitch.analyzer.base;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.zapstitch.analyzer.utils.OauthUtils;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class AppUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public AppUser() {
		tokenCertified = false;
	}

	/**
	 * @param token
	 */
	public AppUser(String token) {
		stateToken = token;
		tokenCertified = false;
	}

	/**
	 * 
	 */
	public void clear() {
		tokenCertified = false;
		username = null;
		email = null;
	}

	private String refreshToken = StringUtils.EMPTY;
	
	private String accessToken = StringUtils.EMPTY;
	
	private boolean tokenCertified;

	private String username = null;

	private String email = null;

	private String stateToken = null;

	private int attempt = 0;

	/**
	 * @return
	 */
	public boolean isTokenCertified() {
		return tokenCertified;
	}

	/**
	 * @param tokenCertified
	 */
	public void setTokenCertified(boolean tokenCertified) {
		this.tokenCertified = tokenCertified;
	}

	/**
	 * @return
	 */
	public int getAttempt() {
		return attempt;
	}

	/**
	 * @param attempt
	 */
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	/**
	 * @return
	 */
	public String getStateToken() {
		return stateToken;
	}

	/**
	 * @param token
	 */
	public void setStateToken(String token) {
		stateToken = token;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * @return
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return
	 */
	public String buildLoginUrl() {
		if (StringUtils.isNotBlank(stateToken)){
			return OauthUtils.getLoginUrl(stateToken);
		}else{
			return Constants.VIEW_ERROR;
		}
	}

}
