package com.zapstitch.analyzer.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.google.api.client.auth.oauth2.Credential;
import com.zapstitch.analyzer.base.AppUser;
import com.zapstitch.analyzer.base.Application;
import com.zapstitch.analyzer.base.Constants;
import com.zapstitch.analyzer.base.Report;
import com.zapstitch.analyzer.base.ZapMail;
import com.zapstitch.analyzer.utils.AppsUtils;
import com.zapstitch.analyzer.utils.GmailUtils;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(true);
		AppUser user = (AppUser) session.getAttribute(Constants.SESSION_APPUSER);
		Report report = (Report) session.getAttribute(Constants.SESSION_REPORT);

		if (report != null) {
			report.clear();
		} else {
			report = new Report();
		}

		String baseDateStr = StringUtils.EMPTY;
		String initDateStr = StringUtils.EMPTY;
		try {
			baseDateStr = request.getParameter(Constants.PARAM_BASE_DATE);
			if (StringUtils.isBlank(baseDateStr)) {
				response.sendRedirect(Constants.VIEW_ERROR);
			}

			report.setEndDate(baseDateStr);
			// add one day in end date and after date, as query is after and before
			baseDateStr = AppsUtils.AddDays(baseDateStr, 1);
			initDateStr = AppsUtils.AddMonth(baseDateStr, -3);
			report.setStartDate(initDateStr);
			initDateStr = AppsUtils.AddDays(initDateStr, -1);

		} catch (ParseException e1) {
			response.sendRedirect(Constants.VIEW_ERROR);
		}

		String query = "after:" + initDateStr + " before:" + baseDateStr;

		try {
//			ArrayList<ZapMail> mails = GmailUtils.getEmailDetails(user.getCredential(),
	//				GmailUtils.listMessagesMatchingQuery(user.getCredential(), query), user.getEmail());
			
			Credential credentials = Application.getInstance().CreateCredential(user.getAccessToken(), user.getRefreshToken());

			ArrayList<ZapMail> mails = GmailUtils.getEmailDetails(credentials,
					GmailUtils.listMessagesMatchingQuery(credentials, query), user.getEmail());
			
			
			report.addtoList(mails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute(Constants.SESSION_REPORT, report);
		response.sendRedirect(Constants.VIEW_DASHBOARD);

	}
}
