package com.deigote.gmailSender
import groovy.transform.CompileStatic

import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeMessage

@Singleton
@CompileStatic
class GmailSender {

	private static final Properties mailServerProperties = buildSmtpProperties()

	public static void main(String[] args) {
		String usageMessage = System.getProperty('usage.message')
		try {
			assert args.size() == 5, "Expected 5 arguments, found ${args.size()}"
			getInstance().send(
				new Email(args[2].tokenize(',').toSet(), args[3], args[4]).validateOrFail(),
				new Credentials(args[0], args[1]).validateOrFail()
			)
		} catch (AssertionError error) {
			throw new IllegalArgumentException("${error.message}. ${usageMessage}", error)
		}
	}

	Email send(Email email, Credentials credentials) {
		Session session = Session.getDefaultInstance(mailServerProperties, null)
		Transport transport = session.getTransport("smtp")
		transport.connect('smtp.gmail.com', credentials.username, credentials.password)
		MimeMessage message = email.setToMimeMessage(new MimeMessage(session))
		transport.sendMessage(message, message.getAllRecipients())
		transport.close()
		return email
	}

	private static Properties buildSmtpProperties() {
		Properties properties = System.getProperties()
		[
			"mail.smtp.port": "587",
			"mail.smtp.auth": "true",
			"mail.smtp.starttls.enable": "true"
		].each { String propertyName, String propertyValue ->
			properties.put(propertyName, propertyValue)
		}
		return properties
	}


}
