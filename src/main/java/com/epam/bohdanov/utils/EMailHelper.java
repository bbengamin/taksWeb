package com.epam.bohdanov.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.epam.bohdanov.controller.exception.DataException;
import com.epam.bohdanov.model.bean.EmailInfoBean;

public class EMailHelper {
	private static final Logger LOG = Logger.getLogger(EMailHelper.class);

	public void send(EmailInfoBean emailInfoBean) {
		Properties props = getProperties();
		Session session = getSession(props);

		try {
			MimeMessage message = createMessage(emailInfoBean, session);
			addRecipients(emailInfoBean, message);

			if (message.getAllRecipients() == null) {
				return;
			}

			MimeMultipart multipart = new MimeMultipart("related");

			multipart.addBodyPart(getBody(emailInfoBean.getTemplate()));

			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			LOG.error("Email send eroor", e);
			throw new DataException(e);
		}
	}

	private Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port", "465");
		return props;
	}

	private BodyPart getBody(String template) throws MessagingException {
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(template, "text/html; charset=utf-8");
		return messageBodyPart;
	}

	private Session getSession(Properties props) {
		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("testseleniumbohdanov@gmail.com", "lazy_cow_9510");
			}
		});
	}

	private MimeMessage createMessage(EmailInfoBean emailInfoBean, Session session) throws MessagingException {
		MimeMessage message = new MimeMessage(session);

		message.setSubject(emailInfoBean.getSubject());
		message.setFrom(new InternetAddress("testseleniumbohdanov@gmail.com"));

		return message;
	}

	private MimeMessage addRecipients(EmailInfoBean emailInfoBean, MimeMessage message) throws MessagingException {
		for (String to : emailInfoBean.getTo()) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		}
		return message;
	}

}
