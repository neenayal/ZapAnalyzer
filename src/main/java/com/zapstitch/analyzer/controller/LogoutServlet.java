package com.zapstitch.analyzer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.Credential;
import com.zapstitch.analyzer.base.AppUser;
import com.zapstitch.analyzer.base.Application;
import com.zapstitch.analyzer.base.Constants;
import com.zapstitch.analyzer.base.Report;
import com.zapstitch.analyzer.utils.AppsUtils;
import com.zapstitch.analyzer.utils.OauthUtils;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(true);
		AppUser user = (AppUser) session.getAttribute(Constants.SESSION_APPUSER);
		Report report = (Report) session.getAttribute(Constants.SESSION_REPORT);
		

		response.setContentType(Constants.PLAIN_CONTENT);
		Credential credentials = Application.getInstance().CreateCredential(user.getAccessToken(), user.getRefreshToken());
	
		boolean isRevoke = OauthUtils.revokeAccess(credentials);

		user.clear();
		AppUser newUser = new AppUser(AppsUtils.generateStateToken());
		newUser.setTokenCertified(false);
		session.setAttribute(Constants.SESSION_APPUSER, newUser);
		if(report != null){
			report.clear();
			session.setAttribute(Constants.SESSION_REPORT, report);
			session.removeAttribute(Constants.SESSION_REPORT);
		}
		if (isRevoke)
			response.sendRedirect(Constants.VIEW_OUT);
		else
			response.sendRedirect(Constants.VIEW_ERROR);

	}
}
