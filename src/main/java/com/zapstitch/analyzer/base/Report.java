package com.zapstitch.analyzer.base;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	ArrayList<ZapMail> mailList = new ArrayList<ZapMail>();
	String startDate = StringUtils.EMPTY;
	String endDate = StringUtils.EMPTY;

	/**
	 * @param mails
	 */
	public void addtoList(ArrayList<ZapMail> mails){
		mailList.addAll(mails);
	}

	/**
	 * @return
	 */
	public ArrayList<ZapMail> getReportList() {
		return mailList;
	}

	/**
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 */
	public void clear() {
		mailList.clear();
		startDate = StringUtils.EMPTY;
		endDate = StringUtils.EMPTY;
	}
}
