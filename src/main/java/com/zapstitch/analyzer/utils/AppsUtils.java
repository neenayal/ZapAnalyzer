package com.zapstitch.analyzer.utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.zapstitch.analyzer.base.Application;
import com.zapstitch.analyzer.base.Constants;


/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com 
 */
public class AppsUtils {

	static SecureRandom random = new SecureRandom();

	/**
	 * Generates a secure state token
	 */
	public static String generateStateToken() {
		return Constants.STATE_IDENTIFIER + random.nextInt();
	}

	/**
	 * @param baseDateStr
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static String AddMonth(String baseDateStr, int month) throws ParseException {

		SimpleDateFormat formatter = Application.getInstance().getAppDateFormatter();
		Date baseDate = formatter.parse(baseDateStr);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(Calendar.MONTH, month);

		Date initDate = cal.getTime();
		String initDateStr = formatter.format(initDate);
		return initDateStr;
	}

	/**
	 * @param baseDateStr
	 * @param days
	 * @return
	 * @throws ParseException
	 */
	public static String AddDays(String baseDateStr, int days) throws ParseException {

		SimpleDateFormat formatter = Application.getInstance().getAppDateFormatter();
		Date baseDate = formatter.parse(baseDateStr);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(Calendar.DATE, days);

		Date initDate = cal.getTime();
		String initDateStr = formatter.format(initDate);
		return initDateStr;
	}

	
	/**
	 * @param address
	 * @return
	 */
	public static ArrayList<String> parseEmails(String address) {

		ArrayList<String> emails = new ArrayList<String>();
		String[] results = address.split(",");

		for (String str : results) {
			if (str.contains("<")) {
				str = str.substring(str.indexOf("<") + 1, str.indexOf(">"));
			}
			str = StringUtils.trimToEmpty(str.replace("\"", ""));
			if (StringUtils.isNotBlank(str)&&StringUtils.contains(str,"@")) {
				emails.add(str);
			}
		}
		return emails;
	}

	/**
	 * @param str
	 * @return
	 */
	public static JSONObject parseJSON(String str){

		JSONParser parser = Application.getInstance().getParser();
		Object obj=null;
		try{
			obj = parser.parse(str);
		} catch (org.json.simple.parser.ParseException e) {

		}

		return (JSONObject) obj;
	}

}
