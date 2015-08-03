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
		String usageMessage = System.getProperty('usage.message')
		assert args.size() == 5, "Wrong number of arguments! ${usageMessage}"
		assert args.every(), "Argument missing! ${usageMessage}"
		getInstance().generateAndSendEmail(*args)
	}

	void generateAndSendEmail(
		String username, String password, String commaSeparatedAddressees, String subject, String body
	) {
		Session session = Session.getDefaultInstance(mailServerProperties, null)
		sendMessage(
			session, username, password,
			buildEmailMessage(session, commaSeparatedAddressees.tokenize(','), subject, body)
		)
	}

	private MimeMessage buildEmailMessage(
		Session session, Collection<String> addressees, String subject, String body
	) {
		MimeMessage message = new MimeMessage(session)
		addressees.each { addressee ->
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressee))
		}
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
