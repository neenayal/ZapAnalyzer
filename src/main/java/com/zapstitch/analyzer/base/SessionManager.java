package com.zapstitch.analyzer.base;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zapstitch.analyzer.utils.AppsUtils;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class SessionManager implements HttpSessionListener {

	/**
	 * Default constructor.
	 */
	public SessionManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent App) {
		AppUser user = new AppUser(AppsUtils.generateStateToken());
		user.setTokenCertified(false);
		App.getSession().setAttribute(Constants.SESSION_APPUSER, user);
		Application.getInstance().init();
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		Application.getInstance().destroy();
	}
}
