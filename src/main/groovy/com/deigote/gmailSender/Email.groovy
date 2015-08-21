package com.deigote.gmailSender
import groovy.transform.ToString

import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@ToString(includePackage = false, includeNames = true)
class Email implements Validateable<Email> {

	final String subject, body
	final Set<String> addressees

	Email(Collection<String> addressees, String subject, String body) {
		this.addressees = addressees.toSet().asImmutable()
		this.subject = subject
		this.body = body
	}

	protected MimeMessage setToMimeMessage(MimeMessage message) {
		addressees
			.collect { new InternetAddress(it) }
			.each { message.addRecipient(Message.RecipientType.TO, it) }
		message.setSubject(subject)
		message.setContent(body, "text/html");
		return message
	}

	@Override
	Set<String> getMandatoryFields() {
		['addressees']
	}
}
