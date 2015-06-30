package com.deigote.gmailSender

import groovy.util.logging.Slf4j

import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Singleton
class GmailSender {

	private static final Properties mailServerProperties = buildSmtpProperties()

	public static void main(String[] args) {
		assert args.findAll { it }.size() == 5, 'Wrong number of arguments! Please provide: <username> <password> <to> <subject> <body>'
		getInstance().generateAndSendEmail(*args)
	}

	private void generateAndSendEmail(
		String username, String password, String to, String subject, String body
	) {
		Session session = Session.getDefaultInstance(mailServerProperties, null)
		sendMessage(
			session, username, password,
			buildEmailMessage(session, to, subject, body)
		)
	}

	private MimeMessage buildEmailMessage(Session session, String to, String subject, String body) {
		MimeMessage message = new MimeMessage(session)
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to))
		message.setSubject(subject)
		message.setContent(body, "text/html");
		return message
	}

	private void sendMessage(Session session, String username, String password, MimeMessage message) {
		Transport transport = session.getTransport("smtp")
		transport.connect("smtp.gmail.com", username, password)
		transport.sendMessage(message, message.getAllRecipients())
		transport.close()
	}

	private static Properties buildSmtpProperties() {
		[
			"mail.smtp.port": "587",
			"mail.smtp.auth": "true",
			"mail.smtp.starttls.enable": "true"
		].inject(System.getProperties()) { properties, propertyName, propertyValue ->
			properties.put(propertyName, propertyValue)
			return properties
		}
	}


}
