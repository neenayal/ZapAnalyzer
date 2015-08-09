package com.zapstitch.analyzer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.google.api.client.auth.oauth2.Credential;
import com.zapstitch.analyzer.base.AppUser;
import com.zapstitch.analyzer.base.Application;
import com.zapstitch.analyzer.base.Constants;
import com.zapstitch.analyzer.utils.AppsUtils;
import com.zapstitch.analyzer.utils.OauthUtils;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(true);
		AppUser user = (AppUser) session.getAttribute(Constants.SESSION_APPUSER);

		String code = request.getParameter(Constants.PARAM_CODE);
		String state = request.getParameter(Constants.PARAM_STATE);

		if (StringUtils.isBlank(code) || StringUtils.isBlank(state)) {
			response.sendRedirect(Constants.VIEW_ERROR);
		} else if (StringUtils.equals(state, user.getStateToken())) {
			Credential credential= OauthUtils.getCredential(code);
			user.setTokenCertified(true);
			
			user.setRefreshToken(credential.getRefreshToken());
			user.setAccessToken(credential.getAccessToken());
			
			fillUserInfo(user);
			session.setAttribute(Constants.SESSION_APPUSER, user);
			response.sendRedirect(Constants.VIEW_DASHBOARD);
		} else {
			response.sendRedirect(Constants.VIEW_ERROR);
		}
	}

	/**
	 * @param user
	 * @throws IOException
	 */
	private void fillUserInfo(AppUser user) throws IOException {

		Credential credentials = Application.getInstance().CreateCredential(user.getAccessToken(), user.getRefreshToken());
		String infoJson = OauthUtils.getUserInfoJson(credentials);
		JSONObject jsonObject = AppsUtils.parseJSON(infoJson);

		String username = (String) jsonObject.get(Constants.JSON_NAME);
		String email = (String) jsonObject.get(Constants.JSON_EMAIL);
		user.setEmail(email);
		user.setUsername(username);
	}

}