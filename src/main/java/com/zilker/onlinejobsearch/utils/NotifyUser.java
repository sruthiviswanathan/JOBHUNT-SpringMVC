package com.zilker.onlinejobsearch.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

import com.zilker.onlinejobsearch.constants.Constants;

/*
 * class for sending mail to a user if a vacancy of his interest is published.
 */
public class NotifyUser {

	public void sendNotification(String recipient) {

		try {
			String to = recipient;
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", Constants.HOST);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");

			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(Constants.FROM));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(Constants.SUBJECT);
			msg.setSentDate(new Date());
			msg.setText(Constants.MESSAGETEXT);

			Transport transport = mailSession.getTransport("smtp");
			transport.connect(Constants.HOST, Constants.USER, Constants.PASS);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
}
