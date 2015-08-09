package com.zapstitch.analyzer.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.zapstitch.analyzer.base.Application;
import com.zapstitch.analyzer.base.Constants;
import com.zapstitch.analyzer.base.ZapMail;

/**
 * @author Neeraj Nayal
 * neeraj.nayal.2008@gmail.com
 */
public class GmailUtils {

	/**
	 * @param credential
	 * @return
	 * @throws IOException
	 */
	private static Gmail getService(final Credential credential) throws IOException {
		return Application.getInstance().getGmailService(credential);
	}

	/**
	 * @param credential
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public static List<Message> listMessagesMatchingQuery(final Credential credential, final String query)
			throws IOException {
		Gmail service = getService(credential);
		String userId = Constants.USER_ITSELF;
		ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

		List<Message> messages = new ArrayList<Message>();
		while (response.getMessages() != null) {
			messages.addAll(response.getMessages());
			if (response.getNextPageToken() != null) {
				String pageToken = response.getNextPageToken();
				response = service.users().messages().list(userId).setQ(query).setPageToken(pageToken).execute();
			} else {
				break;
			}
		}

		return messages;
	}

	/**
	 * getEmailDetails -
	 * using batch, fetch all the mail. and count them.
	 * @param credential
	 * @param msgList
	 * @param user
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<ZapMail> getEmailDetails(final Credential credential, final List<Message> msgList,
			final String user) throws IOException {

		Gmail service = getService(credential);
		final Map<String, Integer> map = new HashMap<String, Integer>();

		BatchRequest batch = service.batch();

		JsonBatchCallback<Message> callback = new JsonBatchCallback<Message>() {

			@Override
			public void onSuccess(Message msg, HttpHeaders responseHeaders) throws IOException {
				MessagePart payload = msg.getPayload();
				List<MessagePartHeader> header = payload.getHeaders();

				// header.
				for (MessagePartHeader head : header) {

					// if it conatnis to,from,cc and bcc
					if (StringUtils.equals(head.getName(), Constants.MAIL_TO)
							|| StringUtils.equals(head.getName(), Constants.MAIL_FROM)
							|| StringUtils.equals(head.getName(), Constants.MAIL_CC)
							|| StringUtils.equals(head.getName(), Constants.MAIL_BCC)) {

						ArrayList<String> adds = AppsUtils.parseEmails(head.getValue());

						// if more than email are there in to,bcc or cc
						for (String value : adds) {
							value = StringUtils.lowerCase(value);
							if (!StringUtils.equalsIgnoreCase(value, user)) { 
								// if its user itself count it
								if (map.containsKey(value)) {
									map.put(value, map.get(value) + 1);
								} else {
									map.put(value, 1);
								}
							}
						}
					} // email counted , if needed

				} // all header covered

			}

			@Override
			public void onFailure(GoogleJsonError arg0, HttpHeaders arg1) throws IOException {
					// don not count this message as message fetching operation fails.
			}
		};

		int batchSize = Application.getInstance().getBatchsize();
		int count = 0;
		for (Message message : msgList) {
			count++;
			service.users().messages().get(user, message.getId()).setFormat(Constants.MAIL_FORMAT)
			.setFields("payload/headers").queue(batch, callback);
			
			if (count == batchSize) {
				batch.execute();
				count = 0;
			}
		}
		if (count > 0)
			batch.execute();

		// put emails in ArrayList
		ArrayList<ZapMail> mailList = new ArrayList<ZapMail>();
		Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			ZapMail zmail = new ZapMail();
			Map.Entry<String, Integer> mapEntry = iterator.next();
			zmail.setEmailId(mapEntry.getKey());
			zmail.setNoOfCommunication(mapEntry.getValue());
			mailList.add(zmail);
		}

		// sort ArrayList
		map.clear();
		Collections.sort(mailList);
		return mailList;
	}

	
	
	/*
	 * 
	 * public static ArrayList<ZapMail> getEmailDetails(final Credential
	 * credential, final List<Message> msgList, final String user) throws
	 * IOException {
	 * 
	 * 
	 * Gmail service = getService(credential); Map<String, Integer> map = new
	 * HashMap<String, Integer>();
	 * 
	 * for (Message message : msgList) {
	 * 
	 * Message msg = getMessage(service, Constants.USER_ITSELF,
	 * message.getId()); MessagePart payload = msg.getPayload();
	 * List<MessagePartHeader> header = payload.getHeaders();
	 * 
	 * for (MessagePartHeader head : header) {
	 * 
	 * // if it conatnis to,from,cc and bcc if
	 * (StringUtils.equals(head.getName(), Constants.MAIL_TO) ||
	 * StringUtils.equals(head.getName(), Constants.MAIL_FROM)||
	 * StringUtils.equals(head.getName(), Constants.MAIL_CC) ||
	 * StringUtils.equals(head.getName(), Constants.MAIL_BCC)){
	 * 
	 * ArrayList<String> adds = AppsUtils.parseEmails(head.getValue());
	 * 
	 * // if more than email are there in to,bcc or cc for (String value : adds)
	 * { if (!StringUtils.equals(value, user)) { // if its user itself //
	 * count++ if (map.containsKey(value)) { map.put(value, map.get(value) + 1);
	 * } else { map.put(value, 1); } } } } // email counted , if needed
	 * 
	 * } // all header covered }
	 * 
	 * // put emails in ArrayList ArrayList<ZapMail> mailList = new
	 * ArrayList<ZapMail>(); Iterator<Map.Entry<String, Integer>> iterator =
	 * map.entrySet().iterator(); while (iterator.hasNext()) { ZapMail zmail =
	 * new ZapMail(); Map.Entry<String, Integer> mapEntry = iterator.next();
	 * zmail.setEmailId(mapEntry.getKey());
	 * zmail.setNoOfCommunication(mapEntry.getValue()); mailList.add(zmail); }
	 * 
	 * // sort ArrayList map.clear(); Collections.sort(mailList); return
	 * mailList; }
	 * 
	 * public static Message getMessage(final Gmail service, final String
	 * userId, final String messageId) throws IOException { List<String>
	 * metadataHeaders = Arrays.asList("From","To","Cc","Bcc");
	 * 
	 * Message message = service.users().messages().get(userId,
	 * messageId).setFormat("metadata").execute(); return message; }
	 * 
	 * 
	 */

}
