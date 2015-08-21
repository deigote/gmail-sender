package com.deigote.gmailSender

import groovy.transform.ToString

@ToString(includePackage = false, includeNames = true)
class Credentials implements Validateable<Credentials> {

	final String username, password

	Credentials(String username, String password) {
		this.username = username
		this.password = password
	}

	@Override
	Set<String> getMandatoryFields() {
		['username', 'password']
	}
}
