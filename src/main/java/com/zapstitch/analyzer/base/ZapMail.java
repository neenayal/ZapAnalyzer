package com.zapstitch.analyzer.base;

import java.io.Serializable;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class ZapMail implements Comparable<ZapMail>, Serializable {

	private static final long serialVersionUID = 1L;
	private String emailId;
	private int noOfCommunication;

	/**
	 * @return
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return
	 */
	public int getNoOfCommunication() {
		return noOfCommunication;
	}

	/**
	 * @param noOfCommunication
	 */
	public void setNoOfCommunication(int noOfCommunication) {
		this.noOfCommunication = noOfCommunication;
	}

	@Override
	public int compareTo(ZapMail zmail) {
		return zmail.getNoOfCommunication() - this.getNoOfCommunication();
	}
}
